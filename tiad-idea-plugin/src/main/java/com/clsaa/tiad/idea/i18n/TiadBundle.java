/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.idea.i18n;

import com.clsaa.tiad.idea.config.TiadConfig;
import com.clsaa.tiad.pmd.I18nResources;
import com.intellij.BundleBase;
import com.intellij.CommonBundle;
import com.intellij.openapi.components.ServiceManager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author clsaa
 */
public class TiadBundle {
    private static TiadConfig tiadConfig = ServiceManager.getService(TiadConfig.class);
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages.TiadBundle",
            new Locale(tiadConfig.getLocale()), new I18nResources.XmlControl());

    static {
        BundleBase.assertOnMissedKeys(false);
    }

    public static String message(String key) {
        return CommonBundle.message(resourceBundle, key);
    }

    public static String message(String key, Object... params) {
        return CommonBundle.message(resourceBundle, key, params).trim();
    }
}
