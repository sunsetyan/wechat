var ioc = {
        dataSource : {
                type : "org.apache.commons.dbcp.BasicDataSource",
                events : {
                        depose : 'close'
                },
                fields : {
                        driverClassName : 'org.h2.Driver',
                        url : 'jdbc:localhost:3306/weifang',
                        username : 'weifang',
                        password : 'titps4gg'
                }
        },
        dao : {
        		type : "org.nutz.dao.impl.NutDao",
        		fields : {
        				dataSource : {refer : 'dataSource'}
        		}
        }
}