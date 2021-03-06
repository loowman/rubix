/**
 * Copyright (c) 2019. Qubole Inc
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package com.qubole.rubix.prestosql;

import com.qubole.rubix.spi.AsyncClusterManager;
import com.qubole.rubix.spi.ClusterType;
import io.prestosql.spi.Node;
import io.prestosql.spi.NodeManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * Created by stagra on 14/1/16.
 */
public class PrestoClusterManager extends AsyncClusterManager
{
  private static Log log = LogFactory.getLog(PrestoClusterManager.class);
  static volatile NodeManager NODE_MANAGER;

  public static void setNodeManager(NodeManager nodeManager)
  {
    PrestoClusterManager.NODE_MANAGER = nodeManager;
  }

  private volatile NodeManager nodeManager;

  @Override
  public void initialize(Configuration conf) throws UnknownHostException
  {
    super.initialize(conf);
    nodeManager = NODE_MANAGER;
    if (nodeManager == null) {
      nodeManager = new StandaloneNodeManager(conf);
    }
  }

  @Override
  public Set<String> getNodesInternal()
  {
    return ClusterManagerNodeGetter.getNodesInternal(nodeManager);
  }

  @Override
  public ClusterType getClusterType()
  {
    return ClusterType.PRESTOSQL_CLUSTER_MANAGER;
  }
}
