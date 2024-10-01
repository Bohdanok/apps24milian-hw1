package ua.edu.ucu.apps.tempseries;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.apache.commons.lang3.ArrayUtils;

public class TemperatureSeriesAnalysis {

    private double[] tempseries;
    private int tempseriesActualValue = 0;
    private int tempseriesLength = 0;
    public static final int aHundred = 100;
    public static final int toNegative = -273;


    public TemperatureSeriesAnalysis() {
        this.tempseries = new double[aHundred];
        System.out.println("HI there!!");
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this.tempseries = temperatureSeries;
        tempseriesActualValue += temperatureSeries.length;
        tempseriesLength = temperatureSeries.length;
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
        double closestToZero = Double.MAX_VALUE;
        for (double element : this.tempseries) {
            if (Math.abs(closestToZero) == element) {
                closestToZero = Math.abs(element);
            }
            if (Math.abs(element) < Math.abs(closestToZero)) {
                closestToZero = element;
            }
        }
        return closestToZero;
    }

    public double findTempClosestToValue(double tempValue) {
        if (ArrayUtils.isEmpty(this.tempseries)) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double closestToElement = Double.MAX_VALUE;
        for (double element : this.tempseries) {
            if ((Math.abs(tempValue - closestToElement)) > Math.abs(
                tempValue - element)) {
                closestToElement = element;
            }
            else if ((Math.abs(tempValue - closestToElement)) == Math
            .abs(tempValue - element)) {
                closestToElement = Math.max(tempValue, closestToElement);
            }
        }
        return closestToElement;
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
        double[] javaFignya = new double[current];
        System.arraycopy(output, 0, javaFignya, 0, current);
        return javaFignya;
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
        double[] javaFignya = new double[current];
        System.arraycopy(output, 0, javaFignya, 0, current);
        return javaFignya;
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
        double[] javaFignya = new double[current];
        System.arraycopy(output, 0, javaFignya, 0, current);
        return javaFignya;
    }

    public void reset() {
        double[] javaFignya = new double[0];
        this.tempseriesLength = 0;
        this.tempseries = javaFignya;
    }

    public double[] sortTemps() {
        double[] cache = this.tempseries.clone();
        Arrays.sort(cache);
        return cache;
    }

    public TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(this.average(), deviation(),
         this.min(), this.max());
    }

    public int addTemps(double... temps) {
        int currentIndex = tempseriesActualValue;

        for (double el : temps) {
            if (el < toNegative) {
                throw new InputMismatchException();
            }
        }

        for (double temp : temps) {
            if (currentIndex == this.tempseriesLength) {
                increaseCapacity(this.tempseriesLength * 2);
            }
            this.tempseries[currentIndex] = temp;
            currentIndex++;
            tempseriesActualValue++;

        }

        return currentIndex;
    }
}
