package ua.edu.ucu.apps.tempseries;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TemperatureSeriesAnalysis {

    private double[] tempseries;
    private int tempseries_actual_value = 0;
    private int tempseries_length = 0;
    public TemperatureSeriesAnalysis() {
        this.tempseries = new double[100];
    }


    public double[] GetTempseries(){
        return this.tempseries;
    }

    private void increase_capacity(int new_capacity){
        double[] copy = new double[new_capacity];
        System.arraycopy(this.tempseries, 0, copy, 0, tempseries_length);
        tempseries = copy;
        tempseries_length = new_capacity;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.tempseries = temperatureSeries;
        tempseries_actual_value += temperatureSeries.length;
        tempseries_length = temperatureSeries.length;
    }

    public double average() {
        if(ArrayUtils.isEmpty(this.tempseries)){
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double average = 0.0;
        for(double element : this.tempseries){
            average += element;
        }
        return average / this.tempseries.length;
    }

    public double deviation() {
        double mean = this.average();
        double variance = 0.0;
        for(double element : this.tempseries){
            variance += Math.pow((element - mean), 2);
        }
        variance = variance / this.tempseries.length;
        return Math.sqrt(variance);
    }

    public double min() {
        if(ArrayUtils.isEmpty(this.tempseries)){
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double min = Double.MAX_VALUE;
        for(double element : this.tempseries){
            if(element < min){
                min = element;
            }
        }
        return min;
    }

    public double max() {
        if(ArrayUtils.isEmpty(this.tempseries)){
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double max = Double.MIN_VALUE;
        for(double element : this.tempseries){
            if(element > max){
                max = element;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        if(ArrayUtils.isEmpty(this.tempseries)){
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double closest_to_zero = Double.MAX_VALUE;
        for(double element : this.tempseries){
            if(Math.abs(closest_to_zero) == element){
                closest_to_zero = Math.abs(element);
            }
            if(Math.abs(element) < Math.abs(closest_to_zero)){
                closest_to_zero = element;
            }
        }
        return closest_to_zero;
    }

    public double findTempClosestToValue(double tempValue) {
        if(ArrayUtils.isEmpty(this.tempseries)){
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double closest_to_element = Double.MAX_VALUE;
        for(double element : this.tempseries){
            if((Math.abs(tempValue - closest_to_element)) > Math.abs(tempValue - element)){
                closest_to_element = element;
            }
            else if((Math.abs(tempValue - closest_to_element)) == Math.abs(tempValue - element)){
                closest_to_element = Math.max(tempValue, closest_to_element);
            }
        }
        return closest_to_element;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] output = new double[tempseries.length];
        int current = 0;
        for(double element : this.tempseries){
            if(tempValue > element){
                output[current] = element;
                current++;
            }
        }
        double[] java_fignua = new double[current];
        System.arraycopy(output, 0, java_fignua, 0, current);
        return java_fignua;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] output = new double[tempseries.length];
        int current = 0;
        for(double element : this.tempseries){
            if(tempValue < element){
                output[current] = element;
                current++;
            }
        }
        double[] java_fignua = new double[current];
        System.arraycopy(output, 0, java_fignua, 0, current);
        return java_fignua;
    }

    public double[] findTempsInRange(double lowerBound, double upperBound) {
        double[] output = new double[tempseries.length];
        int current = 0;
        for(double element : this.tempseries){
            if(lowerBound <= element && upperBound >= element){
                output[current] = element;
                current++;
            } 
        }
        double[] java_fignua = new double[current];
        System.arraycopy(output, 0, java_fignua, 0, current);
        return java_fignua;
    }

    public void reset() {
        double[] java_fignua = new double[0];
        this.tempseries_length = 0;
        this.tempseries = java_fignua ;
    }

    public double[] sortTemps() {
        double[] cache = this.tempseries.clone();
        Arrays.sort(cache);
        return cache;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(this.average(), deviation(), this.min(), this.max());
    }

    public int addTemps(double... temps) {
        int current_index = tempseries_actual_value;

        for(double el : temps){
            if(el < -273){
                throw new InputMismatchException();
            }
        }

        for(double temp : temps){
            if (current_index == this.tempseries_length){
                increase_capacity(this.tempseries_length * 2);
            }
            this.tempseries[current_index] = temp;
            current_index++;
            tempseries_actual_value++;

        }

        return current_index;
    }
}
