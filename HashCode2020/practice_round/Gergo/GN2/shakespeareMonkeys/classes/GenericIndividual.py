import numpy as np
from typing import List
DNAType = List
from GN2.shakespeareMonkeys.interfaces import InterfaceIndividual


class GenericIndividual(InterfaceIndividual.InterfaceIndividual):

    def __init__(self, species: 'Species', creation_type="random", passed_dna:List[int]=None):
        self.species = species
        if(creation_type=="random"):
            self.DNA = self.species.getRandomSequence()
        else:
            if(len(passed_dna)==0):
                raise Exception("There has been no DNA sequence passed. If you want a random descendant, use the creation type='random'")
            self.DNA = passed_dna.astype(np.int)
        self.mutate()


    def calculateFitness(self):
        active_genes = [self.DNA==1]
        active_gene_values = self.species.genome[active_genes]
        active_genes_sum = np.sum(active_gene_values)

        self.fitness = active_genes_sum/self.species.target_sum

        return self.fitness

    def getFitness(self):
        #return self.fitness if self.fitness>0 else np.abs(2*self.fitness)
        return self.fitness


    def mutate(self)->None:
        if(np.random.rand()<=self.species.mutation_rate):
            seq_len = len(self.DNA)
            save_dna = self.DNA.copy()
            #null_genes = np.argwhere([self.DNA==0]).squeeze()
            gene_index = np.random.randint(0, seq_len)
            #gene_to_mutate = null_genes[gene_index]
            self.DNA[gene_index] = 1 if self.DNA[gene_index] == 0 else 0
            #self.DNA[gene_to_mutate] = 1 if self.DNA[gene_index] == 0 else 0
            if np.sum(self.species.genome[[self.DNA == 1]]) > self.species.target_sum:
                self.DNA = self.species.getRandomSequence()
                #self.DNA = save_dna



    def natural_crossover(self, partner: 'GenericIndividual')-> 'GenericIndividual':
        pivot = int(len(self.DNA)/2)
        first_half = self.DNA[0:pivot]
        second_half = partner.DNA[pivot:]
        #This is problematic as poppa and momma may have a higher sequence length
        new_DNA = np.hstack((first_half, second_half))
        #check
        if np.sum(self.species.genome[[new_DNA==1]]) > self.species.target_sum:
            new_DNA= self.species.getRandomSequence()
        return GenericIndividual(self.species, creation_type="natural", passed_dna=new_DNA)

    def random_crossover(self, poppa: 'GenericIndividual')->'GenericIndividual':
        momma_genes = np.aslist(self.DNA)
        poppa_genes = np.aslist(list(poppa.DNA))

        number_of_genes = len(self.DNA)
        momma_rand_indices = np.random.randint(0, number_of_genes, [int(number_of_genes/2)])
        poppa_indeces = list(range(0, number_of_genes))
        not_selected_from_mommy = [i for i in poppa_indeces if i not in momma_rand_indices]

        newborn =  np.array()
        newborn[momma_rand_indices] = momma_genes[momma_rand_indices]
        newborn[not_selected_from_mommy] = poppa_genes[not_selected_from_mommy]

        return GenericIndividual(self.species, creatiion_type="natural", passed_dna=newborn.tostring())