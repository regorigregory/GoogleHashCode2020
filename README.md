# GoogleHashCode2020
My solution for Google Hashcode's library challange (2020) > 26.8 million

Hi!

This is based on a simple (greedy approach) optimisation.

The things that improved on the initial 18M score:

I factored into the number of instances of books into the compareTo method of the book class.
Then, next huge jump, from about 23M was including a devision by the days required to register in the
library's compareTo method.

Apart from that, it is pretty much common-sense.

This has been my first challange of this kind and was surprised that actually my solution, 
which ranked us within the top 200 worldwide, and top 10 in the UK was this straightforward.

Have fun experimenting with "magic numbers" which were introduced to fine tune certain factors in the formula used to calculate the score of each library and book.

Please have a look at the "GreedySolver" as the other approaches are yet to be implemented to provide a comparision basis whether other
approaches yield better results.
