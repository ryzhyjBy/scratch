package com.ryzhyj.inteview.helpers;

import java.io.File;
import java.math.BigDecimal;

public class Params {
    private String configPath;
    private BigDecimal bettingAmount;

    public String getConfigPath() {
        return configPath;
    }

    public BigDecimal getBettingAmount() {
        return bettingAmount;
    }

    public boolean init(String[] args) {
        if (args.length == 0) {
            return false;
        }

        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "-c":
                    case "--config":
                        configPath = args[++i];
                        break;
                    case "-b":
                    case "--betting-amount":
                        try {
                            bettingAmount = new BigDecimal(args[++i]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid betting amount: " + args[i]);
                            return false;
                        }
                        break;
                    default:
                        System.err.println("Unknown argument: " + arg);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid arguments");
        }

        return verify();
    }

    private boolean verify() {
        if (configPath == null) {
            System.err.println("Config path is not specified");
            return false;
        } else {
            if (!new File(configPath).exists()) {
                System.err.println("Config file does not exist: " + configPath);
                return false;
            }
        }

        if (bettingAmount == null || bettingAmount.signum() <= 0) {
            System.err.println("Incorrect betting amount");
            return false;
        }

        return true;
    }
}
