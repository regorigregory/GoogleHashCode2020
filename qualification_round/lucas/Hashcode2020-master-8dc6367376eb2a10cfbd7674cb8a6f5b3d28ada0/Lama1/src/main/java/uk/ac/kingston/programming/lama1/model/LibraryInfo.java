/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.lama1.model;

import java.util.ArrayList;

/**
 *
 * @author Lama1
 */
public class LibraryInfo {
    public Integer Id = 0;
    public Integer TopId = 0;
    public Integer FinalScore = 0;
    public Integer FinalNoOfDaysRequired = 0;
    public Integer MaxScore = 0;
    public Integer MaxScorePerDay = 0;
    public Integer NoOfDays = 0;
    public Integer AvgScore = 0;
    public Integer MaxNoDaysLeft = 0;
    public Integer NoDaysLeft = 0;
    public Integer DaysTaken = 0;
    public Integer Weighting = 0;
    public Integer ShipPerDay = 0;
    public Integer SignUpProcess = 0;
    public ArrayList<Book> Books = new ArrayList<>();
}
