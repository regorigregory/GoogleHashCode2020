import numpy as np
from GN2.shakespeareMonkeys.interfaces.InterfaceSpecies import InterfaceSpecies
from GN2.shakespeareMonkeys.classes.GenericIndividual import GenericIndividual
from typing import List
class Species(InterfaceSpecies):
    def __init__(self, name: str,  mutation_rate: float, seq_len:int, genome: List[int], target)->None:
        self.name = name
        self.genome = np.array(genome, dtype=np.int)
        self.mutation_rate = mutation_rate
        self.DNA_length = seq_len
        self.target_sum = target

    def getRandomCharacter(self):
        pass


    def getRandomSequence(self):
        candidate =  np.random.randint(0, 2, self.DNA_length)
        candidate_mask = np.array(candidate==1).squeeze()
        score = np.sum (self.genome[candidate_mask])
        if score>self.target_sum:
            candidate = self.getRandomSequence()
        return candidate

    def get_new_individual(self):
        return GenericIndividual(self)

