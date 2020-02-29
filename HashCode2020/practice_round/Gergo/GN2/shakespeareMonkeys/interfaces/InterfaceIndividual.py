import abc
from typing import List
class InterfaceIndividual(abc.ABC):

    @property
    def DNA(self):
        return self.__DNA

    @DNA.setter
    def DNA(self, DNA:str):
        # print("New dna has been passed:{0}".format(DNA))
        self.__DNA = DNA

    @abc.abstractmethod
    def __init__(self, species: 'Species', creation_type="random", passed_dna: str = ""):
        pass

    @abc.abstractmethod
    def calculateFitness(self, target):
        pass

    @abc.abstractmethod
    def getFitness(self):
        pass

    @abc.abstractmethod
    def mutate(self) -> None:
        pass

    @abc.abstractmethod
    def natural_crossover(self, partner: 'GenericIndividual') -> 'GenericIndividual':
        pass

    @abc.abstractmethod
    def random_crossover(self, poppa: 'GenericIndividual') -> 'GenericIndividual':
        pass

