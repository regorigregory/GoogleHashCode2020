import sys

class SimpleProgressBar:
    instance = None
    def __init__(self, done_character=">", left_to_do_character = "-"):
        self._done_character = done_character
        self._left_to_do_character = left_to_do_character
    @staticmethod
    def get_instance():
        if (SimpleProgressBar.instance == None):
            SimpleProgressBar.instance=SimpleProgressBar()
        return SimpleProgressBar.instance

    def accuracy_target_bar(self, current_score, params_dict, header = None, progress_bar_steps=50):
        header = "Progress: " if header == None else header
        header = "\r"+header
        done_character = self._done_character
        left_to_do_character = self._left_to_do_character

        have_done = int(current_score*100*(progress_bar_steps/100))
        left_to_do = int(progress_bar_steps - have_done)

        str_completed = " champion_score: {0:.6f} %".format(current_score*100)
        str_progress = " [" + str(done_character * have_done) + str(left_to_do_character * left_to_do) + "]"
        additional_outputs = ""
        for k,v in params_dict.items():
            additional_outputs+=" {0}: {1}".format(k, v)
        output = header+str_progress+str_completed+additional_outputs

        sys.stdout.write(output)

    def progress_bar(self, completed, steps_to_complete, params_dict = None, header = None, progress_bar_steps=20):
        header = "Training: " if header==None else header
        done_character = self._done_character
        left_to_do_character = self._left_to_do_character

        have_done = int(completed / steps_to_complete * progress_bar_steps)
        left_to_do = progress_bar_steps - have_done

        str_completed =  " completed: {0:d} / {1:d} steps".format(int(completed), int(steps_to_complete))
        str_progress = " [" +  str(done_character * have_done) + str(left_to_do_character * left_to_do) +"]"
        str_progress_percentage = " {0:.2f} %".format((completed / steps_to_complete) * 100)
        output = "\r"+ header + str_progress + str_progress_percentage + str_completed

        if (params_dict!=None):
            for k,v in params_dict.items():
                temp =  ", "+k+": "+v
                output+=temp

        sys.stdout.write(output)
    @staticmethod
    def progress_bar_static(completed, steps_to_complete, params_dict=None, header=None, progress_bar_steps=20):
        header = "Training: " if header == None else header
        done_character = SimpleProgressBar.get_instance()._done_character
        left_to_do_character = SimpleProgressBar.et_instance()._left_to_do_character

        have_done = int(completed / steps_to_complete * progress_bar_steps)
        left_to_do = progress_bar_steps - have_done

        str_completed = " completed:"+str(completed) +"/"+str(steps_to_complete)+"steps"
        str_progress = " [" + str(done_character * have_done) + str(left_to_do_character * left_to_do) + "]"
        str_progress_percentage = " "+str((completed / steps_to_complete) * 100)+" %"
        output = "\r" + header + str_progress + str_progress_percentage + str_completed

        if (params_dict != None):
            for k, v in params_dict.items():
                temp = ", " + k + ": " + v
                output += temp

        sys.stdout.write(output)