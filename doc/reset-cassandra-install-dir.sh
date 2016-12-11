sudo rm -rf /var/lib/cassandra;
sudo rm -rf /var/log/cassandra;
sudo rm -rf /var/lib/dsefs;
sudo rm -rf /var/lib/spark;
sudo rm -rf /var/log/spark;
sudo mkdir -p /var/lib/cassandra; sudo chown -R  $USER:$GROUP /var/lib/cassandra
sudo mkdir -p /var/log/cassandra; sudo chown -R  $USER:$GROUP /var/log/cassandra
sudo mkdir -p /var/lib/dsefs; sudo chown -R  $USER:$GROUP /var/lib/dsefs
sudo mkdir -p /var/lib/spark; sudo chown -R  $USER:$GROUP /var/lib/spark
sudo mkdir -p /var/log/spark; sudo chown -R  $USER:$GROUP /var/log/spark
