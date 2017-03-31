/*******************************************************************************
 * Copyright 2016 Generalitat de Catalunya.
 *
 * The contents of this file may be used under the terms of the EUPL, Version 1.1 or - as soon they will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence. You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1 Unless required by applicable law or agreed to in writing, software distributed under the
 * Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations under the Licence.
 *
 * Original authors: Centre de Suport Canigó Contact: oficina-tecnica.canigo.ctti@gencat.cat
 *******************************************************************************/
package cat.gencat.plantillacanigo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource({ "classpath:cat/gencat/ctti/canigo/arch/core/config/canigo-core.xml" })
@EnableTransactionManagement
public class AppConfig {

}
