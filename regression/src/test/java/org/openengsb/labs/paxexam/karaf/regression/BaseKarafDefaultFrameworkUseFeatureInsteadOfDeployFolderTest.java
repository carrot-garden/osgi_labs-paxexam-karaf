/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.labs.paxexam.karaf.regression;

import static junit.framework.Assert.fail;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.provision;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class BaseKarafDefaultFrameworkUseFeatureInsteadOfDeployFolderTest {

    @Inject
    BundleContext bc;

    @Configuration
    public Option[] config() {
        return new Option[]{ karafDistributionConfiguration("mvn:org.apache.karaf/apache-karaf/2.2.5/zip", "karaf",
            "2.2.5").useDeployFolder(false), provision(mavenBundle("org.slf4j", "slf4j-api", "1.6.1")) };
    }

    @Test
    public void test() throws Exception {
        for (Bundle b : bc.getBundles()) {
            if (b.getSymbolicName().equals("slf4j.api")) {
                return;
            }
        }
        fail("slf4j-api is not provisioned");
    }

}
