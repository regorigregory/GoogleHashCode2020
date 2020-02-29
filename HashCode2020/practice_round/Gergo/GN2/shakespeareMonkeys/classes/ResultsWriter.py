import os
class ResultsWriter():
    def __init__(self, output_directory, output_filename):
        self.output_directory = os.path.join(output_directory,output_filename)
    def readMax(self):
        with open(self.output_directory) as f:
            max = int(f.readline())
        return max
    def write_results(self, score, number_of_pizzas, selected_numbers):

        directory = os.path.dirname(self.output_directory)
        if (not os.path.exists(directory)):
            os.makedirs(directory)
        with open(self.output_directory, "w+") as f:
            f.write(str(score)+"\n")
            f.write(str(number_of_pizzas)+"\n")
            f.write(str(selected_numbers)+"\n")
            f.close()
if __name__ == "__main__":
    dir = os.path.join(".", "test_Write")
    file = "test.in"
    writer = ResultsWriter(dir, file)

    score= 10
    num_sclies = 100
    pizzas = [1,2,3,43,54]

    writer.write_results(score, num_sclies, pizzas)
