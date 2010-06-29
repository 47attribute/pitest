/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package com.example;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.pitest.annotations.PITSuiteMethod;
import org.pitest.junit.PITJUnitRunner;

@RunWith(PITJUnitRunner.class)
public class TopLevelSuite {

  // @PITContainer
  // public static Container container() {
  // return new DistributedContainer();
  // }

  @SuppressWarnings("unchecked")
  @PITSuiteMethod
  public static Collection<Class<?>> children() {
    return Arrays.asList(JUnit4SuiteB.class, JUnit4SuiteA.class,
        JUnit4SuiteB.class, JUnit4SuiteA.class, JUnit4SuiteB.class,
        JUnit4SuiteA.class, JUnit4SuiteB.class, JUnit4SuiteA.class,
        JUnit4SuiteB.class, JUnit4SuiteA.class, JUnit4SuiteB.class,
        JUnit4SuiteA.class, JUnit4SuiteB.class, JUnit4SuiteA.class,
        JUnit4SuiteB.class, JUnit4SuiteA.class, JUnit4SuiteB.class,
        JUnit4SuiteA.class);
  }

}
