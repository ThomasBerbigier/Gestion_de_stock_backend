package com.thomas.gestionDeStock.inspector;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.util.StringUtils;

public class MyStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        // Manipulez ou inspectez ici la requête SQL
        System.out.println("SQL avant exécution: " + sql);

        if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            if(sql.contains("where")) {
                sql = sql + "and idEntreprise = 1";
            } else {
                sql = sql + "where idEntreprise = 1";
            }
        }
        // Retournez la requête SQL (modifiable) ou null pour l'ignorer
        return sql;
    }

}
