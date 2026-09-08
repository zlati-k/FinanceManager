package com.financemanager.app.util;

import java.util.Locale;

public final class MoneyFormat {

    private MoneyFormat() {
    }

    public static String formatBgn(double amount) {
        return String.format(Locale.forLanguageTag("bg-BG"), "€%.2f.", amount);
    }
}
