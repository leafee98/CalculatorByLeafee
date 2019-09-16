package com.example.calculatorbyleafee.ui.converter;

public class ConverterModel {

    public static double convert(String ConvertType, String originalUnit, double original,
                          String targetUnit) {
        // Units.put("Length", Arrays.asList("nanometers", "microns", "millimeters", "centimeters",
        //         "meters", "kilometers", "inches", "yards", "miles", "nautical miles"));

        double base = 0, target = 0;
        if (ConvertType.equals("Length")) {
            if (originalUnit.equals("nanometers")) {
                base = original / 1;
            } else if (originalUnit.equals("microns")) {
                base = original / 1e3;
            } else if (originalUnit.equals("millimeters")) {
                base = original / 1e6;
            } else if (originalUnit.equals("centimeters")) {
                base = original / 1e7;
            } else if (originalUnit.equals("meters")) {
                base = original / 1e9;
            } else if (originalUnit.equals("kilometers")) {
                base = original / 1e12;
            } else if (originalUnit.equals("inches")) {
                base = original / 1e7 / 2.54;
            } else if (originalUnit.equals("yards")) {
                base = original / 1e7 / 2.54 / 36;
            } else if (originalUnit.equals("miles")) {
                base = original / 1e7 / 2.54 / 36 / 1760;
            } else if (originalUnit.equals("nautical miles")) {
                base = original / 1e12 / 1.852;
            }

            if (targetUnit.equals("nanometers")) {
                target = base * 1;
            } else if (targetUnit.equals("microns")) {
                target = base * 1e3;
            } else if (targetUnit.equals("millimeters")) {
                target = base * 1e6;
            } else if (targetUnit.equals("centimeters")) {
                target = base * 1e7;
            } else if (targetUnit.equals("meters")) {
                target = base * 1e9;
            } else if (targetUnit.equals("kilometers")) {
                target = base * 1e12;
            } else if (targetUnit.equals("inches")) {
                target = base * 1e7 * 2.54;
            } else if (targetUnit.equals("yards")) {
                target = base * 1e7 * 2.54 * 36;
            } else if (targetUnit.equals("miles")) {
                target = base * 1e7 * 2.54 * 36 * 1760;
            } else if (targetUnit.equals("nautical miles")) {
                target = base * 1e12 * 1.852;
            }

        } else if (ConvertType.equals("Time")) {
            //  Units.put("Time", Arrays.asList("microseconds", "milliseconds", "seconds", "minutes",
            //          "hours", "days", "weeks", "years"));
            if (originalUnit.equals("microseconds")) {
                base = original / 1;
            } else if (originalUnit.equals("milliseconds")) {
                base = original / 1e3;
            } else if (originalUnit.equals("seconds")) {
                base = original / 1e6;
            } else if (originalUnit.equals("minutes")) {
                base = original / 1e6 / 60;
            } else if (originalUnit.equals("hours")) {
                base = original / 1e6 / 3600;
            } else if (originalUnit.equals("days")) {
                base = original / 1e6 / 3600 / 24;
            } else if (originalUnit.equals("weeks")) {
                base = original / 1e6 / 3600 / 24 / 7;
            } else if (originalUnit.equals("years")) {
                base = original / 1e6 / 3600 / 24 / 365;
            }

            if (targetUnit.equals("microseconds")) {
                target = base * 1;
            } else if (targetUnit.equals("milliseconds")) {
                target = base * 1e3;
            } else if (targetUnit.equals("seconds")) {
                target = base * 1e6;
            } else if (targetUnit.equals("minutes")) {
                target = base * 1e6 * 60;
            } else if (targetUnit.equals("hours")) {
                target = base * 1e6 * 3600;
            } else if (targetUnit.equals("days")) {
                target = base * 1e6 * 3600 * 24;
            } else if (targetUnit.equals("weeks")) {
                target = base * 1e6 * 3600 * 24 * 7;
            } else if (targetUnit.equals("years")) {
                target = base * 1e6 * 3600 * 24 * 365;
            }
        } else if (ConvertType.equals("Data")) {
            //  Units.put("Data", Arrays.asList("Bits", "Bytes", "KibiBits", "KibiBytes",
            //          "MebiBits", "MebiBytes", "GibiBits", "GibiBytes"));
            if (originalUnit.equals("Bits")) {
                base = original / 1;
            } else if (originalUnit.equals("Bytes")) {
                base = original / 8;
            } else if (originalUnit.equals("KibiBits")) {
                base = original / 1024;
            } else if (originalUnit.equals("KibiBytes")) {
                base = original / 8 / 1024;
            } else if (originalUnit.equals("MebiBits")) {
                base = original / 1024 / 1024;
            } else if (originalUnit.equals("MebiBytes")) {
                base = original / 8 / 1024 / 1024;
            } else if (originalUnit.equals("GibiBits")) {
                base = original / 1024 / 1024 / 1024;
            } else if (originalUnit.equals("GibiBytes")) {
                base = original / 8 / 1024 / 1024 / 1024;
            }


            if (targetUnit.equals("Bits")) {
                target = base * 1;
            } else if (targetUnit.equals("Bytes")) {
                target = base * 8;
            } else if (targetUnit.equals("KibiBits")) {
                target = base * 1024;
            } else if (targetUnit.equals("KibiBytes")) {
                target = base * 8 * 1024;
            } else if (targetUnit.equals("MebiBits")) {
                target = base * 1024 * 1024;
            } else if (targetUnit.equals("MebiBytes")) {
                target = base * 8 * 1024 * 1024;
            } else if (targetUnit.equals("GibiBits")) {
                target = base * 1024 * 1024 * 1024;
            } else if (targetUnit.equals("GibiBytes")) {
                target = base * 8 * 1024 * 1024 * 1024;
            }
        }

        return target;
    }
}
