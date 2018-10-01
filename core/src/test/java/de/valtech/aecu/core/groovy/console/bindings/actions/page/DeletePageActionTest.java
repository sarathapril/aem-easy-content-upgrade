/*
 * Copyright 2018 Valtech GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.valtech.aecu.core.groovy.console.bindings.actions.page;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

import de.valtech.aecu.core.groovy.console.bindings.impl.BindingContext;

/**
 * Tests DeletePageAction
 * 
 * @author Roland Gruber
 */
@RunWith(MockitoJUnitRunner.class)
public class DeletePageActionTest {

    @Mock
    private PageManager pageManager;

    @Mock
    private BindingContext context;

    @Mock
    private Resource resource;

    @Mock
    private Page page;

    private DeletePageAction action;

    @Before
    public void setup() {
        when(context.getPageManager()).thenReturn(pageManager);
        this.action = new DeletePageAction(context);
        when(pageManager.getContainingPage(resource)).thenReturn(page);
        when(resource.getPath()).thenReturn("path");
    }

    @Test
    public void doAction_pageNotFound() throws PersistenceException {
        when(pageManager.getContainingPage(resource)).thenReturn(null);

        String result = action.doAction(resource);

        assertTrue(result.contains("Unable to find a page"));
    }

    @Test(expected = PersistenceException.class)
    public void doAction_pagemanagerException() throws PersistenceException, WCMException {
        doThrow(WCMException.class).when(pageManager).delete(page, false);

        action.doAction(resource);
    }

    @Test
    public void doAction_success() throws PersistenceException, WCMException {
        String result = action.doAction(resource);

        assertTrue(result.contains("Deleted page"));
    }

    @Test
    public void doAction_successDryRun() throws PersistenceException, WCMException {
        when(context.isDryRun()).thenReturn(true);

        String result = action.doAction(resource);

        assertTrue(result.contains("Deleted page"));
    }

}
