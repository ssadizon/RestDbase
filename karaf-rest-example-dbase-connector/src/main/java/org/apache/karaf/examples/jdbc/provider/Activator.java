/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.karaf.examples.jdbc.provider;

import javax.sql.DataSource;

import org.apache.karaf.examples.rest.api.BookingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

    private ServiceRegistration<BookingService> serviceRegistration;
    private ServiceTracker<DataSource, DataSource> dataSourceTracker;
    private ServiceRegistration<BookingService> clientServiceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
    	Filter filter = bundleContext.createFilter("(&" + "(" + Constants.OBJECTCLASS
                + "=" + DataSource.class.getName() + ")(osgi.jndi.service.name=jdbc/karaf-example))");
    	
    	System.out.println("Provider starting...");
        dataSourceTracker = new ServiceTracker<DataSource, DataSource>(bundleContext, filter, null) {

            @Override
            public DataSource addingService(ServiceReference<DataSource> reference) {
            	DataSource ds = (DataSource) bundleContext.getService(reference);
            	System.out.println("Got a DataSource!");

                // create a booking service impl instance
            	BookingServiceJdbcImpl bookingService = new BookingServiceJdbcImpl(ds);
                // registering the booking service in the service registry
                serviceRegistration = bundleContext.registerService(BookingService.class, bookingService, null);

                return ds;
            }

            @Override
            public void removedService(ServiceReference<DataSource> reference, DataSource service) {
                clientServiceRegistration.unregister();
            }
        };      

        dataSourceTracker.open();
        System.out.println("Provider started!");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        if (serviceRegistration != null) {
            // remove the service from the service registry
            serviceRegistration.unregister();
        }
    }
}
