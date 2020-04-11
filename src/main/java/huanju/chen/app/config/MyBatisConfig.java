package huanju.chen.app.config;

import huanju.chen.app.domain.dto.*;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author HuanJu
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
                TypeAliasRegistry registry = configuration.getTypeAliasRegistry();
//                registry.registerAliases("huanju.chen.app.domain.dto");
                registry.registerAlias(BankAccount.class);
                registry.registerAlias(CashAccount.class);
                registry.registerAlias(Information.class);
                registry.registerAlias(LedgerAccount.class);
                registry.registerAlias(Proof.class);
                registry.registerAlias(ProofItem.class);
                registry.registerAlias(SubAccount.class);
                registry.registerAlias(Subject.class);
                registry.registerAlias(User.class);
                registry.registerAlias(BigDecimal.class);
                registry.registerAlias(SumMoney.class);

            }
        };
    }
}
