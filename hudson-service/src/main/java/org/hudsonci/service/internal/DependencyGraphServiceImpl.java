/**
 * The MIT License
 *
 * Copyright (c) 2010-2011 Sonatype, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.hudsonci.service.internal;

import static org.hudsonci.service.internal.ServicePreconditions.*;
import hudson.model.BuildListener;
import hudson.model.DependencyGraph;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildTrigger;

import javax.inject.Singleton;

import org.hudsonci.service.DependencyGraphService;


/**
 * Default implementation of {@link DependencyGraphService}.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 2.1.0
 */
@Singleton
public class DependencyGraphServiceImpl extends ServiceSupport implements DependencyGraphService {

    public DependencyGraph getGraph() {
        return getHudson().getDependencyGraph();
    }

    public void rebuild() {
        log.debug("Rebuilding dependency graph");
        getHudson().rebuildDependencyGraph();
    }

    public void triggerDependents(final AbstractBuild<?, ?> build, final TaskListener listener) {
        checkNotNull(build, "build");
        checkNotNull(listener, "listener");

        if (log.isDebugEnabled()) {
            log.debug("Maybe triggering dependents of build: {}", build.getFullDisplayName());
        }

        // FIXME: In a more perfect world, the BuildTrigger would use this
        // service, instead of us calling it directly here
        BuildTrigger.execute(build, (BuildListener) listener);
    }
}
