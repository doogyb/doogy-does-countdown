$(document).ready(function() {

  var timerLength = 30;
  var steps = timerLength * 100
  var clockInterval = (1000 * timerLength) / steps

  var gameType = "letters";

  $("#answer_display").hide();

  $("#numbers_container").hide();
  $("#number_buttons").hide();

  $("#number_selector").click(function() {

    gameType = "numbers";

    $("#number_selector").prop('disabled', true)
    $("#letter_selector").prop('disabled', false)

    $("#numbers_container").show();
    $("#number_buttons").show();
    $("#letters_container").hide();
    $("#letter_buttons").hide();

    reset();
  });

  $("#letter_selector").click(function() {

    gameType = "letters";

    $("#number_selector").prop('disabled', false)
    $("#letter_selector").prop('disabled', true)

    $("#numbers_container").hide();
    $("#number_buttons").hide();
    $("#letters_container").show();
    $("#letter_buttons").show();

    reset();

  });

  function rotateClockHand() {
    // var seconds = new Date().getSeconds();
    var sdegree = (currentStep / steps) * (timerLength * 6);
    currentStep += 1;
    var srotate = "rotate(" + sdegree + "deg)";

    if (currentStep <= steps) {

      $("#clock_hand").css({
        "-moz-transform": srotate,
        "-webkit-transform": srotate
      });
    } else {
      $("#answer_button").prop('disabled', false)
      clearInterval(countdown);
    }
  }

  function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
  }

  var currentScramble = 0;
  var scrambleLimit = 20;
  var randomInt;

  function randomScramble() {
    if (currentScramble <= scrambleLimit) {
      randomInt = getRandomInt(1000);
      $("#target").html(randomInt);
      currentScramble += 1;
    } else {
      clearInterval(scramble)
      $("#start_timer").prop('disabled', false);
      letters += randomInt.toString();
    }
  }

  var scramble;
  $("#generate").click(function() {
    currentScramble = 0;
    scramble = setInterval(randomScramble, 100);
  })

  var countdown;
  $("#start_timer").click(function() {
    var audio = new Audio('audio/countdown.mp3');
    audio.play();
    currentStep = 1;
    countdown = setInterval(rotateClockHand, clockInterval);
    $("#start_timer").prop('disabled', true);
  });

  var currentLetter = 0;

  function reset() {
    $("#answer_button").prop('disabled', true);
    $("#start_timer").prop('disabled', true);
    $("#answer_display").hide();
    $(".letter_button").prop('disabled', false);
    $("#generate").prop('disabled', true);

    currentLetter = 0;
    letters = ""

    $("#letters").children().each(function() {
      $(this).find(".letter_box").text("");
    });
    $("#numbers").children().each(function() {
      $(this).find(".letter_box").text("");
    });
    $("#target").text("");
    $("#clock_hand").css({
      "-moz-transform": "rotate(0deg)",
      "-webkit-transform": "rotate(0deg)"
    });
  }

  $("#reset_timer").click(reset);

  var letters = ""

  function addLetter(letterType) {
    $.ajax({
      type: 'GET',
      url: 'http://localhost:8080/random/' + letterType.data,
      contentType: 'text/plain',
      success: function(data) {
        letters += data
        $("#letters").children().eq(currentLetter).find(".letter_box").text(data.toUpperCase())
        currentLetter += 1;
        if (currentLetter == 9) {
          $("#start_timer").prop('disabled', false)
          $(".letter_button").prop('disabled', true);
        }
      }
    });
  }

  function addNumber(numberType) {
    $.ajax({
      type: 'GET',
      url: 'http://localhost:8080/random/' + numberType.data,
      contentType: 'text/plain',
      success: function(data) {
        letters += data + ","
        $("#numbers").children().eq(currentLetter).find(".letter_box").text(data)
        currentLetter += 1;
        if (currentLetter == 6) {
          $("#generate").prop('disabled', false);
          $(".letter_button").prop('disabled', true);
        }
      }
    });
  }

  $("#vowel").click('vowel', addLetter);
  $("#consonant").click('consonant', addLetter);
  $("#large").click('large', addNumber);
  $("#small").click('small', addNumber);

  var answerLimit = 5;

  function populateTable(data) {

    /* Note that the whole content variable is just a string */
    var content = "<table class='table table-hover table-striped'>";
    content += "<thead><tr>";
    answers = [];

    $.each(data, function(index, value) {
      content += "<th scope = 'col'>" + index + "</th>";
      answers.push(value);
    });
    content += "</tr></thead>";
    content += "<tbody>";

    for (var row = 0; row < 150; row++) {
      // content += "<tr><th scope='row'>" + row + "</th>"
      var filled = false
      $.each(data, function(key, value) {

        if (row < value.length) {
          ans = value[row]
          filled = true
        } else {
          ans = ""
        }

        content += "<td>" + ans + "</td>"
      });
      content += "</tr>";
      if (!filled) {
        break;
      }
    }

    content += "</thead></table>"
    $("#answer_display").html(content);
    $("#answer_display").show();

    $("html, body").animate({
      scrollTop: $(document).height()
    }, 50);

  }

  function displayNumberAnswer(data) {

    console.log(data);

    content = "<div class='row'><div class='number_display col d-flex justify-content-center'>"
    content += data[0] + "</div></div>"
    content += "<div class='row'><div class='number_display col d-flex justify-content-center'>"
    content += data[1].replaceAll("\n", "<br>") + "</div></div>"

    $("#answer_display").html(content);
    $("#answer_display").show();

    $("html, body").animate({
      scrollTop: $(document).height()
    }, 50);

  }

  $("#answer_button").click(function() {
    if (gameType == "letters") {
      $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/answers/letters/' + letters,
        contentType: "text/plain",
        success: populateTable
      });
    }
    if (gameType == "numbers") {
      console.log(letters);
      $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/answers/numbers/' + letters,
        contentType: "text/plain",
        success: displayNumberAnswer
      });
    }
  });

});