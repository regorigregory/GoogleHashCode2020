from GN2.shakespeareMonkeys.classes.Species import Species
from GN2.shakespeareMonkeys.classes.Evolution import Evolution
from GN2.shakespeareMonkeys.classes.ResultsWriter import ResultsWriter
if __name__ == "__main__":
    import os
    import numpy as np
    np.random.seed(101)
    input_file_path = os.path.join(".", "shakespeareMonkeys", "inputs")
    input_files = [entry for entry in os.scandir(input_file_path) if entry.is_file()==True]
    #for input_file in input_files:
    with open(input_files[3].path) as f:
        print(f.name)
        file_contents = f.readlines()
    needed_amount_of_slices, number_of_types = np.array(file_contents[0][:-1].split(" ")).astype(np.int)
    slices_per_pizza_type = np.array(file_contents[1][:-1].split(" ")).astype(np.int)


    rate = 0.95

    dir = os.path.join(".", "shakespeareMonkeys", "outputs", "1")
    filename = "9n_d_ab.in"

    name="Pizza monkey"
    sp = Species(name, rate, number_of_types, slices_per_pizza_type, needed_amount_of_slices)
    non_elites_ratio = 0.90
    evolution = Evolution(sp, 45, rate, non_elites_ratio)
    evolution.configure_filewriter(dir, filename)
    evolution.start()
