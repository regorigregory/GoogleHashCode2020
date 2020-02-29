/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.hashcode.model;

import uk.ac.kingston.programming.hashcode.model.DataFileIn;
import uk.ac.kingston.programming.hashcode.model.DataFileOut;

/**
 *
 * @author lucas
 */
public interface Solution {
    public String getName();
    public DataFileOut solve(DataFileIn dataFileIn);
}
