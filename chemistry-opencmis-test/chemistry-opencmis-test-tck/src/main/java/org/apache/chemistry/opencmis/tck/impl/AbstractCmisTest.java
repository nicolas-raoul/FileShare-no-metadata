/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.chemistry.opencmis.tck.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.tck.CmisTest;
import org.apache.chemistry.opencmis.tck.CmisTestResult;
import org.apache.chemistry.opencmis.tck.CmisTestResultStatus;
import org.junit.Test;

/**
 * Base class for tests.
 */
public abstract class AbstractCmisTest implements CmisTest {

    private Map<String, String> parameters;
    private AbstractCmisTestGroup group;
    private String name;
    private boolean isEnabled = true;
    private List<CmisTestResult> results;
    private long time;

    public void init(Map<String, String> parameters) {
        this.parameters = parameters;
        results = new ArrayList<CmisTestResult>();
    }

    protected Map<String, String> getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(AbstractCmisTestGroup group) {
        this.group = group;
    }

    public AbstractCmisTestGroup getGroup() {
        return group;
    }

    public abstract void run() throws Exception;

    @Test
    public void junit() {
        JUnitHelper.run(this);
    }

    public List<CmisTestResult> getResults() {
        return results;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public CmisTestResultImpl createResult(CmisTestResultStatus status, String message, Throwable exception,
            boolean isFatal) {
        return new CmisTestResultImpl(group.getName(), name, message, status, exception, isFatal);
    }

    public CmisTestResultImpl createResult(CmisTestResultStatus status, String message, boolean isFatal) {
        return new CmisTestResultImpl(group.getName(), name, message, status, null, isFatal);
    }

    public CmisTestResultImpl createResult(CmisTestResultStatus status, String message) {
        return new CmisTestResultImpl(group.getName(), name, message, status, null, false);
    }

    public CmisTestResultImpl createInfoResult(String message) {
        return new CmisTestResultImpl(group.getName(), name, message, CmisTestResultStatus.INFO, null, false);
    }

    public void addResult(CmisTestResult result) {
        if (result != null) {
            if (result instanceof CmisTestResultImpl) {
                ((CmisTestResultImpl) result).setStackTrace(getStackTrace());
            }

            results.add(result);
            if (result.isFatal()) {
                throw new FatalTestException();
            }
        }
    }

    protected StackTraceElement[] getStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement[] shortStackTrace = new StackTraceElement[0];
        if (stackTrace.length > 3) {
            shortStackTrace = new StackTraceElement[stackTrace.length - 3];
            System.arraycopy(stackTrace, 3, shortStackTrace, 0, stackTrace.length - 3);
        }

        return shortStackTrace;
    }

    protected CmisTestResult addResultChild(CmisTestResult result, CmisTestResult child) {
        if (result == null) {
            return null;
        }

        result.getChildren().add(child);

        return result;
    }

    // --- asserts ----

    protected boolean isEqual(Object expected, Object actual) {
        if (expected == null && actual == null) {
            return true;
        }

        if (expected != null && expected.equals(actual)) {
            return true;
        }

        return false;
    }

    protected CmisTestResult assertIsTrue(Boolean test, CmisTestResult success, CmisTestResult failure) {
        if (test != null && test) {
            return success;
        }

        if (test == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Null!"));
        } else {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "False!"));
        }
    }

    protected CmisTestResult assertIsFalse(Boolean test, CmisTestResult success, CmisTestResult failure) {
        if (test != null && !test) {
            return success;
        }

        if (test == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Null!"));
        } else {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "True!"));
        }
    }

    protected CmisTestResult assertNull(Object object, CmisTestResult success, CmisTestResult failure) {
        if (object == null) {
            return success;
        }

        return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Object is not null!"));
    }

    protected CmisTestResult assertNotNull(Object object, CmisTestResult success, CmisTestResult failure) {
        if (object != null) {
            return success;
        }

        return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Object is null!"));
    }

    protected CmisTestResult assertStringNullOrEmpty(String str, CmisTestResult success, CmisTestResult failure) {
        if (str == null || str.length() == 0) {
            return success;
        }

        return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "String has this value: " + str));
    }

    protected CmisTestResult assertStringNotEmpty(String str, CmisTestResult success, CmisTestResult failure) {
        if (str != null && str.length() > 0) {
            return success;
        }

        if (str == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "String is null!"));
        } else {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "String is empty!"));
        }
    }

    protected CmisTestResult assertListNotEmpty(List<?> list, CmisTestResult success, CmisTestResult failure) {
        if (list != null && list.size() > 0) {
            return success;
        }

        if (list == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "List is null!"));
        } else {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "List is empty!"));
        }
    }

    protected CmisTestResult assertEquals(Object expected, Object actual, CmisTestResult success, CmisTestResult failure) {
        if (isEqual(expected, actual)) {
            return success;
        }

        return addResultChild(failure,
                createResult(CmisTestResultStatus.INFO, "expected: " + expected + " / actual: " + actual));
    }

    protected CmisTestResult assertContains(Collection<?> collection, Object value, CmisTestResult success,
            CmisTestResult failure) {
        if (collection == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Collection is null!"));
        }

        if (collection.contains(value)) {
            return success;
        }

        return addResultChild(failure,
                createResult(CmisTestResultStatus.INFO, "Collection does not contain '" + value + "'"));
    }

    protected CmisTestResult assertEqualLists(List<?> expected, List<?> actual, CmisTestResult success,
            CmisTestResult failure) {
        if (expected == null && actual == null) {
            return success;
        }

        if (expected == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Expected list is null!"));
        }

        if (actual == null) {
            return addResultChild(failure, createResult(CmisTestResultStatus.INFO, "Actual list is null!"));
        }

        if (expected.size() != actual.size()) {
            return addResultChild(
                    failure,
                    createResult(CmisTestResultStatus.INFO, "List sizes don't match! expected: " + expected.size()
                            + " / actual: " + actual.size()));
        }

        for (int i = 0; i < expected.size(); i++) {
            if (!isEqual(expected, actual)) {
                return addResultChild(
                        failure,
                        createResult(CmisTestResultStatus.INFO, "expected list item[" + i + "]: " + expected
                                + " / actual list item[" + i + "]: " + actual));
            }
        }

        return success;
    }
}