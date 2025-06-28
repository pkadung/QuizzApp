/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.pkadung.utils.theme;

import javafx.scene.Scene;

/**
 *
 * @author admin
 */
public enum Theme {
    DEFAULT {
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setThemFactory(new DefaultThemeFactory());
            ThemeManager.applyTheme(scene);
        }
    }, DARK {
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setThemFactory(new DarkThemeFactory());
            ThemeManager.applyTheme(scene);
        }
    }, LIGHT { 
        @Override
        public void updateTheme(Scene scene) {
            ThemeManager.setThemFactory(new LightThemeFactory());
            ThemeManager.applyTheme(scene);
        }
    };

    public abstract void updateTheme(Scene scene);
}
