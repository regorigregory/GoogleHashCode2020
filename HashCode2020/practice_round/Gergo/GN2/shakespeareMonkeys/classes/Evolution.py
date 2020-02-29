import numpy as np
import math
from GN2.shakespeareMonkeys.classes.GenericIndividual import GenericIndividual
from GN2.shakespeareMonkeys.classes.SimpleProgressBar import SimpleProgressBar
from GN2.shakespeareMonkeys.classes.ResultsWriter import ResultsWriter

from typing import List
class Evolution:
    def __init__(self, species:'Species',  population_size: int, mutation_rate: float, non_elites_ratio):
        self.species = species
        self.population_size = population_size
        self.mutation_rate = mutation_rate
        self.evolutional_target = self.species.target_sum
        self.non_elites_ratio = non_elites_ratio

        self.individual_count = 0
        self.evolutional_round = 0


        self.current_population = np.array([])
        self.mating_pool = np.array([])
        self.all_fitness = np.array([])
        self.winner = None
        self.last_champion =  None
        self.last_champion_score = 0

    @property
    def evolutional_target(self):
        return self.__evolutional_target

    @evolutional_target.setter
    def evolutional_target(self, target: str):
        self.__evolutional_target = target
    def configure_filewriter(self, directory, file):
        self.writer = ResultsWriter(directory, file)
    def generate_starting_population(self):
        self.evolutional_round += 1
        self.individual_count += self.population_size
        self.current_population = np.array([self.species.get_new_individual() for i in range(self.population_size)])
        self.calculate_fitness()

    def calculate_fitness(self):
        self.all_fitness = np.asarray([])
        for individual in self.current_population:
            current_fitness = individual.calculateFitness()
            current_max = np.sum(self.species.genome[[individual.DNA==1]])
            if(current_fitness==1 ):
                self.winner = individual
            if(current_max>self.last_champion_score):
                self.last_champion_score = current_max
                self.last_champion = individual
            self.all_fitness = np.append(self.all_fitness, current_fitness)
    def get_elites(self):
        x = np.argsort(self.all_fitness)
        elite_pivot = 0-int(self.population_size*self.non_elites_ratio)
        x = x[::-1]
        return x[:elite_pivot]

    def generate_mating_pool(self):
        max_fitness = np.max(self.all_fitness)
        elites = self.current_population
        if self.evolutional_round != 1:
            elite_indeces = self.get_elites()
            elites = self.current_population[elite_indeces]

        self.mating_pool = np.array([])

        for individual in elites:
            instances = int(individual.getFitness()/max_fitness*100)
            for i in range(instances):
                self.mating_pool = np.append(self.mating_pool, individual)
                if(self.mating_pool.shape[0]>self.population_size):
                    break;


    def do_mating_season(self):
        new_population = np.array([])
        for i in range(self.population_size):
            mommy: GenericIndividual = self.mating_pool[np.random.randint(0, self.mating_pool.shape[0])]
            poppy: GenericIndividual = self.mating_pool[np.random.randint(0, self.mating_pool.shape[0])]

            baby: GenericIndividual = mommy.natural_crossover(poppy)
            new_population = np.append(new_population, baby)
        self.current_population = new_population
        self.calculate_fitness()


    def start(self):
        self.evolutional_round = 0
        self.individual_count = 0
        self.winner: GenericIndividual = None
        self.generate_starting_population()
        pb_instance = SimpleProgressBar.get_instance()
        while(self.winner== None):
            self.evolutional_round +=1
            self.individual_count += self.population_size
            self.generate_mating_pool()
            self.do_mating_season()

            if(self.evolutional_round%100==0):
                champion_index = np.argmax(self.all_fitness)
                champion: GenericIndividual = self.current_population[champion_index]
                add_output_dict = {}
                add_output_dict = {"Word champion score DNA":str(self.last_champion_score)}
                add_output_dict["Signed champion delta"]= str(champion.getFitness())
                #add_output_dict["Champion DNA"]= str(champion.DNA)
                add_output_dict["Last champion score"] = np.sum(self.species.genome[champion.DNA ==1])
                add_output_dict["Round"]= str(self.evolutional_round)
                add_output_dict["Count"]= str(self.individual_count)
                pb_instance.accuracy_target_bar(champion.getFitness(), add_output_dict)
                self.writer.write_results(self.last_champion_score, np.sum(self.last_champion.DNA==1), " ".join([str(i) for i in np.argwhere(self.last_champion.DNA==1).squeeze()]))

        print("\r\n")
        print("The evolution was successful after {0} rounds and {1} individuals".format(self.evolutional_round, self.individual_count))
        print("The winner solution is: {0}".format((self.winner.DNA)))
        original_dna = self.species.genome
        print("The sequence given was: {0}".format(original_dna))

        winner_values = self.species.genome[self.winner.DNA==1]
        winner_sum = np.sum(winner_values)
        pizza_nums = np.argwhere(self.winner.DNA==1).flatten()
        pizza_nums = [str(i) for i in pizza_nums.tolist()]
        print(winner_values)
        print(winner_sum)
        print(len(pizza_nums))
        print(" ".join(pizza_nums))
        self.writer.write_results(winner_sum, np.sum(pizza_nums),
                                 " ".join([str(i) for i in winner_values]))
