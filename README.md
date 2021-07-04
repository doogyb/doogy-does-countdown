![banner](img/doogy-does-countdown.png)

---

**Doogy Does Countdown** is a free online clone of the popular words and numbers puzzle gameshow [Countdown](https://en.wikipedia.org/wiki/Countdown_(game_show)).<br>
This particular repository is for the backend, which was my first foray into creating a simple REST API using Kotlin.

You can find the repository for the frontend [here](https://github.com/doogyb)<br>
You can find a hosted version of the game [here](https://doogyb.me)

## Installation

To install and run a version of this backend, do the following:

```bash
# installs the API
./gradlew installDist
# runs the API
./build/install/doogy-does-countdown/bin/doogy-does-countdown
```

## API calls

You can then query the following endpoints using GET requests:
```
# Random generator

# random vowel generation
/random/vowel

# random consonant generation
/random/consonant

# random large number
/random/large

#random small number
/random/small

# Solution engine

# solutions to letters
# {letters} is simple string of lowercase letters, no seperator needed
# size of the string can range between 5 to 9
# change this in source if needed
/answers/letters/{letters}

# solutions to numbers
# numbers are comma delimited, last number is the target number
/answers/numbers/{numbers}
```

## Examples

### Letters 


The letters endpoint returns a json dictionary where the key is the length of the words and the value is the list of words that can be made from the given letters. I've ommitted a lot of answers to conserve screen space.

`/answers/letters/retainsit`

```json
{
   "5":["..."],
   "6":["..."],
   "7":[
      "airiest",
      "anestri",
      "antsier",
      "artiest",
      "artiste",
      "asterin",
      "atenist",
      "attires",
      "..."
   ],
   "8":[
      "antritis",
      "artinite",
      "inertias",
      "intreats",
      "nitrates",
      "nitrites",
      "rainiest",
      "satinite",
      "sittinae",
      "straiten",
      "tertians"
   ]
}
```

### Numbers

The numbers returns a json list, (really a tuple) where the first element is the closest possible answer and the second element is the answer in string format:


`answers/numbers/2,4,6,8,25,50,654`
```json
[
   "654.0",
   "2 + 4 = 6\n6 + 25 = 31\n31 + 50 = 81\n81 * 8 = 648\n648 + 6 = 654\n"
]
```

The answer printed out "nicely":

```
2 + 4 = 6
6 + 25 = 31
31 + 50 = 81
81 * 8 = 648
648 + 6 = 654
```


