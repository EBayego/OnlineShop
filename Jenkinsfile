pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/EBayego/OnlineShop'
            }
        }

		stage('Build Microservices') {
            parallel {
                stage('Build Customer-Service') {
                    steps {
                        dir('customer-service') {
                            sh 'mvn clean package'
                            sh 'docker build -t ebayego/customer-service:latest .'
                        }
                    }
                }
				
				stage('Build Inventory-Service') {
                    steps {
                        dir('inventory-service') {
                            sh 'mvn clean package'
                            sh 'docker build -t ebayego/inventory-service:latest .'
                        }
                    }
                }

                stage('Build Order-Service') {
                    steps {
                        dir('order-service') {
                            sh 'mvn clean package'
                            sh 'docker build -t ebayego/order-service:latest .'
                        }
                    }
                }
				
				stage('Build Product-Service') {
                    steps {
                        dir('product-service') {
                            sh 'mvn clean package'
                            sh 'docker build -t ebayego/product-service:latest .'
                        }
                    }
                }
            }
        }

        stage('Test') {
            parallel {
                stage('Test Customer-Service') {
                    steps {
                        dir('customer-service') {
                            sh 'mvn test'
                        }
                    }
                }

                stage('Test Inventory-Service') {
                    steps {
                        dir('inventory-service') {
                            sh 'mvn test'
                        }
                    }
                }

                stage('Test Order-Service') {
                    steps {
                        dir('order-service') {
                            sh 'mvn test'
                        }
                    }
                }

                stage('Test Product-Service') {
                    steps {
                        dir('product-service') {
                            sh 'mvn test'
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying to production...'
                // deployment scripts
				sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
