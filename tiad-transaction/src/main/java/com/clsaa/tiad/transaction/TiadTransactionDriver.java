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

package com.clsaa.tiad.transaction;

import com.clsaa.tiad.transaction.agent.TransactionStub;

public class TiadTransactionDriver {
    private String appName;

    public static TiadTransactionDriver tiad(String appName) {
        return new TiadTransactionDriver(appName);
    }

    public TiadTransactionDriver(String appName) {
        this.appName = appName;
    }

    public void init(String packagePath) {
        new TransactionStub().scan(packagePath);
    }
}
