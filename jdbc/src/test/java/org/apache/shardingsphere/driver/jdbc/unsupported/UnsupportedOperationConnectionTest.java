/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.driver.jdbc.unsupported;

import org.apache.shardingsphere.driver.jdbc.core.connection.ShardingSphereConnection;
import org.apache.shardingsphere.infra.exception.generic.UnsupportedSQLOperationException;
import org.apache.shardingsphere.infra.metadata.database.rule.RuleMetaData;
import org.apache.shardingsphere.mode.manager.ContextManager;
import org.apache.shardingsphere.transaction.rule.TransactionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnsupportedOperationConnectionTest {
    
    private ShardingSphereConnection shardingSphereConnection;
    
    @BeforeEach
    void setUp() {
        ContextManager contextManager = mock(ContextManager.class, RETURNS_DEEP_STUBS);
        when(contextManager.getMetaDataContexts().getMetaData().getGlobalRuleMetaData()).thenReturn(new RuleMetaData(Collections.singleton(mock(TransactionRule.class, RETURNS_DEEP_STUBS))));
        shardingSphereConnection = new ShardingSphereConnection("foo_db", contextManager, null);
    }
    
    @SuppressWarnings("JDBCResourceOpenedButNotSafelyClosed")
    @Test
    void assertPrepareCall() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.prepareCall("foo_call"));
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.prepareCall("foo_call", ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY));
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.prepareCall("foo_call",
                ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT));
    }
    
    @Test
    void assertNativeSQL() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.nativeSQL(""));
    }
    
    @Test
    void assertAbort() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.abort(null));
    }
    
    @Test
    void assertGetTypeMap() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::getTypeMap);
    }
    
    @Test
    void assertSetTypeMap() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.setTypeMap(null));
    }
    
    @Test
    void assertGetNetworkTimeout() {
        assertDoesNotThrow(shardingSphereConnection::getNetworkTimeout);
    }
    
    @Test
    void assertSetNetworkTimeout() {
        assertDoesNotThrow(() -> shardingSphereConnection.setNetworkTimeout(null, 0));
    }
    
    @Test
    void assertCreateArrayOf() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.createArrayOf("", null));
    }
    
    @Test
    void assertCreateBlob() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::createBlob);
    }
    
    @Test
    void assertCreateClob() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::createClob);
    }
    
    @Test
    void assertCreateNClob() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::createNClob);
    }
    
    @Test
    void assertCreateSQLXML() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::createSQLXML);
    }
    
    @Test
    void assertCreateStruct() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.createStruct("", null));
    }
    
    @Test
    void assertGetClientInfo() {
        assertThrows(SQLFeatureNotSupportedException.class, shardingSphereConnection::getClientInfo);
    }
    
    @Test
    void assertGetClientInfoWithName() {
        assertThrows(SQLFeatureNotSupportedException.class, () -> shardingSphereConnection.getClientInfo(""));
    }
    
    @Test
    void assertSetClientInfo() {
        assertThrows(UnsupportedSQLOperationException.class, () -> shardingSphereConnection.setClientInfo("", ""));
    }
    
    @Test
    void assertSetClientInfoWithProperties() {
        assertThrows(UnsupportedSQLOperationException.class, () -> shardingSphereConnection.setClientInfo(new Properties()));
    }
}
