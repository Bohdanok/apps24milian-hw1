package ua.edu.ucu.apps.tempseries;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;
import lombok.Setter;

public class TemperatureSeriesAnalysis {

    private double[] tempseries;
    private int tempseriesActualValue = 0;
    private int tempseriesLength = 0;
    public static final int AHUNDRED =100;
    public TemperatureSeriesAnalysis() {
        this.tempseries = new double[AHUNDRED];
        System.out.println("HI there!!");
    }


    public double[] getTempseries() {
        return tempseries;
    }

    private void increaseCapacity(int newCapacity) { 
        double[] copy = new double[newCapacity];
        System.arraycopy(this.tempseries, 0, copy, 0, tempseriesLength);
        tempseries = copy;
        tempseriesLength = newCapacity;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.tempseries = temperatureSeries;
        tempseriesActualValue += temperatureSeries.length;
        tempseriesLength = temperatureSeries.length;
    }

    public double average() {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double average = 0.0;
        for (double element : this.tempseries) {
            average += element;
        }
        return average / this.tempseries.length;
    }

    public double deviation() {
        double mean = this.average();
        double variance = 0.0;
        for (double element : this.tempseries) {
            variance += (element - mean) * (element - mean);
        }
        variance = variance / this.tempseries.length;
        return Math.sqrt(variance);
    }

    public double min() {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double min = Double.MAX_VALUE;
        for (double element : this.tempseries) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }

    public double max() {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double max = Double.MIN_VALUE;
        for (double element : this.tempseries) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double closest_to_zero = Double.MAX_VALUE;
        for (double element : this.tempseries) {
            if (Math.abs(closest_to_zero) == element) {
                closest_to_zero = Math.abs(element);
            }
            if (Math.abs(element) < Math.abs(closest_to_zero)) {
                closest_to_zero = element;
            }
        }
        return closest_to_zero;
    }

    public double findTempClosestToValue(double tempValue) {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double closest_to_element = Double.MAX_VALUE;
        for (double element : this.tempseries) {
            if ((Math.abs(tempValue - closest_to_element)) > Math.abs(tempValue - element)) {
                closest_to_element = element;
            }
            else if ((Math.abs(tempValue - closest_to_element)) == Math.abs(tempValue - element)) {
                closest_to_element = Math.max(tempValue, closest_to_element);
            }
        }
        return closest_to_element;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] output = new double[tempseries.length];
        int current = 0;
        for (double element : this.tempseries) {
            if (tempValue > element) {
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
        for (double element : this.tempseries) {
            if (tempValue < element) {
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
        for (double element : this.tempseries) {
            if (lowerBound <= element && upperBound >= element) {
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
        this.tempseriesLength = 0;
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
        int current_index = tempseriesActualValue;

        for (double el : temps) {
            if (el < -273) {
                throw new InputMismatchException();
            }
        }

        for (double temp : temps) {
            if (current_index == this.tempseriesLength) {
                increaseCapacity(this.tempseriesLength * 2);
            }
            this.tempseries[current_index] = temp;
            current_index++;
            tempseriesActualValue++;

        }

        return current_index;
    }
}
