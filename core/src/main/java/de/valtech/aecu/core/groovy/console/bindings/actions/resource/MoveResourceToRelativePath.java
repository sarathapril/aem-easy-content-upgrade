/*
 *  Copyright 2018 Valtech GmbH
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>
 */
package de.valtech.aecu.core.groovy.console.bindings.actions.resource;

import de.valtech.aecu.core.groovy.console.bindings.actions.Action;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import javax.annotation.Nonnull;

/**
 * @author Roxana Muresan
 */
public class MoveResourceToRelativePath implements Action {

    private String relativePath;
    private ResourceResolver resourceResolver;

    public MoveResourceToRelativePath(@Nonnull String relativePath, @Nonnull ResourceResolver resourceResolver) {
        this.relativePath = relativePath;
        this.resourceResolver = resourceResolver;
    }

    @Override
    public String doAction(@Nonnull Resource resource) throws PersistenceException {
        Resource destinationResource = resourceResolver.getResource(resource, relativePath);
        String sourceAbsPAth = resource.getPath();
        String destinationAsPath = destinationResource.getPath();
        resourceResolver.move(sourceAbsPAth, destinationAsPath);

        return "Moved " + sourceAbsPAth + " to path " + destinationAsPath;
    }
}
