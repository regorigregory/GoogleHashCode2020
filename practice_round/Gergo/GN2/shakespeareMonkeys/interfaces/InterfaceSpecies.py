import abc
from typing import List
import  numpy as np
class InterfaceSpecies(abc.ABC):

    @abc.abstractmethod
    def __init__(self, name: str, genome: str, mutation_rate: float, seq_len: int)->None:
       pass

    @abc.abstractmethod
    def getRandomCharacter(self)->str:
        pass

    @abc.abstractmethod
    def getRandomSequence(self)->List[str]:
        pass

    @property
    def genome(self):
        return self.__DNA

    @genome.setter
    def genome(self, DNA:str):
        self.__DNA = np.array(DNA, dtype=np.int)

