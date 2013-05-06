/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010 by Red Hat Inc and/or its affiliates or by
 * third-party contributors as indicated by either @author tags or express
 * copyright attribution statements applied by the authors.  All
 * third-party contributions are distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.metamodel.spi.relational;

import org.hibernate.dialect.Dialect;

/**
 * Models a SQL <tt>INDEX</tt> defined as UNIQUE
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class UniqueKey extends AbstractConstraint {

	public static final String GENERATED_NAME_PREFIX = "UK";

	protected UniqueKey(Table table, String name) {
		super( table, name );
	}

	@Override
	public String getExportIdentifier() {
		StringBuilder sb = new StringBuilder( getTable().getLoggableValueQualifier() );
		sb.append( ".UK" );
		for ( Column column : getColumns() ) {
			sb.append( '_' ).append( column.getColumnName().getText() );
		}
		return sb.toString();
	}

	@Override
	public String[] sqlCreateStrings(Dialect dialect) {
		return new String[] { dialect.getUniqueDelegate().getAlterTableToAddUniqueKeyCommand( this ) };
	}

	@Override
	public String[] sqlDropStrings(Dialect dialect) {
		return new String[] { dialect.getUniqueDelegate().getAlterTableToDropUniqueKeyCommand( this ) };
	}

	@Override
    public String sqlConstraintStringInAlterTable(Dialect dialect) {
		// not used
		return "";
	}
}
