package com.example.calculatorbyleafee.ui.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConverterModel {

    static Map<String, List<String>> supportUnits;

    static {
        supportUnits = new HashMap<>();
        supportUnits.put("Types", Arrays.asList("Length", "Time", "Data", "Volume"));

        supportUnits.put("Volume", Arrays.asList("Milliliters", "Cubic centimeters",
                "Liters", "Cubic meters"));
        supportUnits.put("Length", Arrays.asList("nanometers", "microns", "millimeters", "centimeters",
                "meters", "kilometers", "inches", "yards", "miles", "nautical miles"));
        supportUnits.put("Time", Arrays.asList("microseconds", "milliseconds", "seconds", "minutes",
                "hours", "days", "weeks", "years"));
        supportUnits.put("Data", Arrays.asList("Bits", "Bytes", "KibiBits", "KibiBytes",
                "MebiBits", "MebiBytes", "GibiBits", "GibiBytes"));
    }

    static double convert(String ConvertType, String originalUnit, double original,
                          String targetUnit) {

        double base = 0, target = 0;
        switch (ConvertType) {
            case "Volume":
                switch (originalUnit) {
                    case "Milliliters":
                    case "Cubic centimeters":
                        base = original * 1;
                        break;
                    case "Liters":
                        base = original * 1000;
                        break;
                    case "Cubic meters":
                        base = original * 1000000;
                        break;
                }

                switch (targetUnit) {
                    case "Milliliters":
                    case "Cubic centimeters":
                        target = base / 1;
                        break;
                    case "Liters":
                        target = base / 1000;
                        break;
                    case "Cubic meters":
                        target = base / 1000000;
                        break;
                }
            case "Length":
                switch (originalUnit) {
                    case "nanometers":
                        base = original * 1;
                        break;
                    case "microns":
                        base = original * 1e3;
                        break;
                    case "millimeters":
                        base = original * 1e6;
                        break;
                    case "centimeters":
                        base = original * 1e7;
                        break;
                    case "meters":
                        base = original * 1e9;
                        break;
                    case "kilometers":
                        base = original * 1e12;
                        break;
                    case "inches":
                        base = original * 1e7 * 2.54;
                        break;
                    case "yards":
                        base = original * 1e7 * 2.54 * 36;
                        break;
                    case "miles":
                        base = original * 1e7 * 2.54 * 36 * 1760;
                        break;
                    case "nautical miles":
                        base = original * 1e12 * 1.852;
                        break;
                }

                switch (targetUnit) {
                    case "nanometers":
                        target = base / 1;
                        break;
                    case "microns":
                        target = base / 1e3;
                        break;
                    case "millimeters":
                        target = base / 1e6;
                        break;
                    case "centimeters":
                        target = base / 1e7;
                        break;
                    case "meters":
                        target = base / 1e9;
                        break;
                    case "kilometers":
                        target = base / 1e12;
                        break;
                    case "inches":
                        target = base / 1e7 / 2.54;
                        break;
                    case "yards":
                        target = base / 1e7 / 2.54 / 36;
                        break;
                    case "miles":
                        target = base / 1e7 / 2.54 / 36 / 1760;
                        break;
                    case "nautical miles":
                        target = base / 1e12 / 1.852;
                        break;
                }

                break;
            case "Time":
                switch (originalUnit) {
                    case "microseconds":
                        base = original * 1;
                        break;
                    case "milliseconds":
                        base = original * 1e3;
                        break;
                    case "seconds":
                        base = original * 1e6;
                        break;
                    case "minutes":
                        base = original * 1e6 * 60;
                        break;
                    case "hours":
                        base = original * 1e6 * 3600;
                        break;
                    case "days":
                        base = original * 1e6 * 3600 * 24;
                        break;
                    case "weeks":
                        base = original * 1e6 * 3600 * 24 * 7;
                        break;
                    case "years":
                        base = original * 1e6 * 3600 * 24 * 365;
                        break;
                }

                switch (targetUnit) {
                    case "microseconds":
                        target = base / 1;
                        break;
                    case "milliseconds":
                        target = base / 1e3;
                        break;
                    case "seconds":
                        target = base / 1e6;
                        break;
                    case "minutes":
                        target = base / 1e6 / 60;
                        break;
                    case "hours":
                        target = base / 1e6 / 3600;
                        break;
                    case "days":
                        target = base / 1e6 / 3600 / 24;
                        break;
                    case "weeks":
                        target = base / 1e6 / 3600 / 24 / 7;
                        break;
                    case "years":
                        target = base / 1e6 / 3600 / 24 / 365;
                        break;
                }
                break;
            case "Data":
                //  Units.put("Data", Arrays.asList("Bits", "Bytes", "KibiBits", "KibiBytes",
                //          "MebiBits", "MebiBytes", "GibiBits", "GibiBytes"));
                switch (originalUnit) {
                    case "Bits":
                        base = original * 1;
                        break;
                    case "Bytes":
                        base = original * 8;
                        break;
                    case "KibiBits":
                        base = original * 1024;
                        break;
                    case "KibiBytes":
                        base = original * 8 * 1024;
                        break;
                    case "MebiBits":
                        base = original * 1024 * 1024;
                        break;
                    case "MebiBytes":
                        base = original * 8 * 1024 * 1024;
                        break;
                    case "GibiBits":
                        base = original * 1024 * 1024 * 1024;
                        break;
                    case "GibiBytes":
                        base = original * 8 * 1024 * 1024 * 1024;
                        break;
                }


                switch (targetUnit) {
                    case "Bits":
                        target = base / 1;
                        break;
                    case "Bytes":
                        target = base / 8;
                        break;
                    case "KibiBits":
                        target = base / 1024;
                        break;
                    case "KibiBytes":
                        target = base / 8 / 1024;
                        break;
                    case "MebiBits":
                        target = base / 1024 / 1024;
                        break;
                    case "MebiBytes":
                        target = base / 8 / 1024 / 1024;
                        break;
                    case "GibiBits":
                        target = base / 1024 / 1024 / 1024;
                        break;
                    case "GibiBytes":
                        target = base / 8 / 1024 / 1024 / 1024;
                        break;
                }
                break;
        }

        return target;
    }
}
