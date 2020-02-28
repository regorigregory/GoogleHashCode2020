# GoogleHashCode2020
One of our solution for Google Hashcode's library challange (2020) > 26.8 million



Contents: 
    GreedeySolver:
        This is based on a simple (greedy approach) optimisation algorithm.

        The things that improved on the initial 18M score:
        
        I factored into the number of instances of books into the compareTo method of the book class.
        
        Then, next huge jump, from about 20M was including a devision by the days required to register in the
        library's compareTo method which was used to get their order.
        
        Another tweak was to raise these "score" functions a.k.a. divisors to some power to find magic numbers which added a few more 100K.
        Apart from that, it is pretty much common-sense.
        
        Have fun experimenting with these "magic numbers", changing the comparators etc. 

        
    Other solvers are in progress.
    
    Intended to be done/uploaded: 
        1. Including Lucas's brute force approach
        2. Uploading the first solution for the practice round:
            A: Genetic algorithm in python
            B: Google's ORTools -> knapsack solver -> Jupyter notebook
            
        2. Genetic algorithm based optimisation. Have started, not implemented.
        3. Knapsack solver inspired optimisation. Have started, not implemented.
        4. Data analysis and visualisation for greater insight. Have not even started.




This has been my first challange of this kind and was surprised that actually my solution, 
which ranked us within the top 200 worldwide, and top 10 in the UK was this straightforward.


Please have a look at the "GreedySolver" as the other approaches are yet to be implemented to provide a comparision basis whether other
approaches yield better results.

Please feel free to contribute, make recommendations...as well.

