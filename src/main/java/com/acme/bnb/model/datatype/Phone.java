package com.acme.bnb.model.datatype;

import com.acme.bnb.controlers.clases.Validable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@NoArgsConstructor
@Data
@Embeddable
public class Phone implements Serializable, Validable {

    public static final Map<String, Country> COUNTRIES = new HashMap<String, Country>() {
        {
            put("AFG", new Country("Afganistán", "AF", "AFG", 93));
            put("ALB", new Country("Albania", "AL", "ALB", 355));
            put("DEU", new Country("Alemania", "DE", "DEU", 49));
            put("AND", new Country("Andorra", "AD", "AND", 376));
            put("AGO", new Country("Angola", "AO", "AGO", 244));
            put("AIA", new Country("Anguila", "AI", "AIA", 1264));
            put("ATA", new Country("Antártida", "AQ", "ATA", 672));
            put("ATG", new Country("Antigua y Barbuda", "AG", "ATG", 1268));
            put("SAU", new Country("Arabia Saudita", "SA", "SAU", 966));
            put("DZA", new Country("Argelia", "DZ", "DZA", 213));
            put("ARG", new Country("Argentina", "AR", "ARG", 54));
            put("ARM", new Country("Armenia", "AM", "ARM", 374));
            put("ABW", new Country("Aruba", "AW", "ABW", 297));
            put("AUS", new Country("Australia", "AU", "AUS", 61));
            put("AUT", new Country("Austria", "AT", "AUT", 43));
            put("AZE", new Country("Azerbaiyán", "AZ", "AZE", 994));
            put("BEL", new Country("Bélgica", "BE", "BEL", 32));
            put("BHS", new Country("Bahamas", "BS", "BHS", 1242));
            put("BHR", new Country("Bahrein", "BH", "BHR", 973));
            put("BGD", new Country("Bangladesh", "BD", "BGD", 880));
            put("BRB", new Country("Barbados", "BB", "BRB", 1246));
            put("BLZ", new Country("Belice", "BZ", "BLZ", 501));
            put("BEN", new Country("Benín", "BJ", "BEN", 229));
            put("BTN", new Country("Bhután", "BT", "BTN", 975));
            put("BLR", new Country("Bielorrusia", "BY", "BLR", 375));
            put("MMR", new Country("Birmania", "MM", "MMR", 95));
            put("BOL", new Country("Bolivia", "BO", "BOL", 591));
            put("BIH", new Country("Bosnia y Herzegovina", "BA", "BIH", 387));
            put("BWA", new Country("Botsuana", "BW", "BWA", 267));
            put("BRA", new Country("Brasil", "BR", "BRA", 55));
            put("BRN", new Country("Brunéi", "BN", "BRN", 673));
            put("BGR", new Country("Bulgaria", "BG", "BGR", 359));
            put("BFA", new Country("Burkina Faso", "BF", "BFA", 226));
            put("BDI", new Country("Burundi", "BI", "BDI", 257));
            put("CPV", new Country("Cabo Verde", "CV", "CPV", 238));
            put("KHM", new Country("Camboya", "KH", "KHM", 855));
            put("CMR", new Country("Camerún", "CM", "CMR", 237));
            put("CAN", new Country("Canadá", "CA", "CAN", 1));
            put("TCD", new Country("Chad", "TD", "TCD", 235));
            put("CHL", new Country("Chile", "CL", "CHL", 56));
            put("CHN", new Country("China", "CN", "CHN", 86));
            put("CYP", new Country("Chipre", "CY", "CYP", 357));
            put("VAT", new Country("Ciudad del Vaticano", "VA", "VAT", 39));
            put("COL", new Country("Colombia", "CO", "COL", 57));
            put("COM", new Country("Comoras", "KM", "COM", 269));
            put("COG", new Country("República del Congo", "CG", "COG", 242));
            put("COD", new Country("República Democrática del Congo", "CD", "COD", 243));
            put("PRK", new Country("Corea del Norte", "KP", "PRK", 850));
            put("KOR", new Country("Corea del Sur", "KR", "KOR", 82));
            put("CIV", new Country("Costa de Marfil", "CI", "CIV", 225));
            put("CRI", new Country("Costa Rica", "CR", "CRI", 506));
            put("HRV", new Country("Croacia", "HR", "HRV", 385));
            put("CUB", new Country("Cuba", "CU", "CUB", 53));
            put("CWU", new Country("Curazao", "CW", "CWU", 5999));
            put("DNK", new Country("Dinamarca", "DK", "DNK", 45));
            put("DMA", new Country("Dominica", "DM", "DMA", 1767));
            put("ECU", new Country("Ecuador", "EC", "ECU", 593));
            put("EGY", new Country("Egipto", "EG", "EGY", 20));
            put("SLV", new Country("El Salvador", "SV", "SLV", 503));
            put("ARE", new Country("Emiratos Árabes Unidos", "AE", "ARE", 971));
            put("ERI", new Country("Eritrea", "ER", "ERI", 291));
            put("SVK", new Country("Eslovaquia", "SK", "SVK", 421));
            put("SVN", new Country("Eslovenia", "SI", "SVN", 386));
            put("ESP", new Country("España", "ES", "ESP", 34));
            put("USA", new Country("Estados Unidos de América", "US", "USA", 1));
            put("EST", new Country("Estonia", "EE", "EST", 372));
            put("ETH", new Country("Etiopía", "ET", "ETH", 251));
            put("PHL", new Country("Filipinas", "PH", "PHL", 63));
            put("FIN", new Country("Finlandia", "FI", "FIN", 358));
            put("FJI", new Country("Fiyi", "FJ", "FJI", 679));
            put("FRA", new Country("Francia", "FR", "FRA", 33));
            put("GAB", new Country("Gabón", "GA", "GAB", 241));
            put("GMB", new Country("Gambia", "GM", "GMB", 220));
            put("GEO", new Country("Georgia", "GE", "GEO", 995));
            put("GHA", new Country("Ghana", "GH", "GHA", 233));
            put("GIB", new Country("Gibraltar", "GI", "GIB", 350));
            put("GRD", new Country("Granada", "GD", "GRD", 1473));
            put("GRC", new Country("Grecia", "GR", "GRC", 30));
            put("GRL", new Country("Groenlandia", "GL", "GRL", 299));
            put("GLP", new Country("Guadalupe", "GP", "GLP", 590));
            put("GUM", new Country("Guam", "GU", "GUM", 1671));
            put("GTM", new Country("Guatemala", "GT", "GTM", 502));
            put("GUF", new Country("Guayana Francesa", "GF", "GUF", 594));
            put("GGY", new Country("Guernsey", "GG", "GGY", 44));
            put("GIN", new Country("Guinea", "GN", "GIN", 224));
            put("GNQ", new Country("Guinea Ecuatorial", "GQ", "GNQ", 240));
            put("GNB", new Country("Guinea-Bissau", "GW", "GNB", 245));
            put("GUY", new Country("Guyana", "GY", "GUY", 592));
            put("HTI", new Country("Haití", "HT", "HTI", 509));
            put("HND", new Country("Honduras", "HN", "HND", 504));
            put("HKG", new Country("Hong kong", "HK", "HKG", 852));
            put("HUN", new Country("Hungría", "HU", "HUN", 36));
            put("IND", new Country("India", "IN", "IND", 91));
            put("IDN", new Country("Indonesia", "ID", "IDN", 62));
            put("IRN", new Country("Irán", "IR", "IRN", 98));
            put("IRQ", new Country("Irak", "IQ", "IRQ", 964));
            put("IRL", new Country("Irlanda", "IE", "IRL", 353));
            put("IMN", new Country("Isla de Man", "IM", "IMN", 44));
            put("CXR", new Country("Isla de Navidad", "CX", "CXR", 61));
            put("NFK", new Country("Isla Norfolk", "NF", "NFK", 672));
            put("ISL", new Country("Islandia", "IS", "ISL", 354));
            put("BMU", new Country("Islas Bermudas", "BM", "BMU", 1441));
            put("CYM", new Country("Islas Caimán", "KY", "CYM", 1345));
            put("CCK", new Country("Islas Cocos (Keeling)", "CC", "CCK", 61));
            put("COK", new Country("Islas Cook", "CK", "COK", 682));
            put("ALA", new Country("Islas de Åland", "AX", "ALA", 358));
            put("FRO", new Country("Islas Feroe", "FO", "FRO", 298));
            put("SGS", new Country("Islas Georgias del Sur y Sandwich del Sur", "GS", "SGS", 500));
            put("MDV", new Country("Islas Maldivas", "MV", "MDV", 960));
            put("FLK", new Country("Islas Malvinas", "FK", "FLK", 500));
            put("MNP", new Country("Islas Marianas del Norte", "MP", "MNP", 1670));
            put("MHL", new Country("Islas Marshall", "MH", "MHL", 692));
            put("PCN", new Country("Islas Pitcairn", "PN", "PCN", 870));
            put("SLB", new Country("Islas Salomón", "SB", "SLB", 677));
            put("TCA", new Country("Islas Turcas y Caicos", "TC", "TCA", 1649));
            put("UMI", new Country("Islas Ultramarinas Menores de Estados Unidos", "UM", "UMI", 246));
            put("VGB", new Country("Islas Vírgenes Británicas", "VG", "VGB", 1284));
            put("VIR", new Country("Islas Vírgenes de los Estados Unidos", "VI", "VIR", 1340));
            put("ISR", new Country("Israel", "IL", "ISR", 972));
            put("ITA", new Country("Italia", "IT", "ITA", 39));
            put("JAM", new Country("Jamaica", "JM", "JAM", 1876));
            put("JPN", new Country("Japón", "JP", "JPN", 81));
            put("JEY", new Country("Jersey", "JE", "JEY", 44));
            put("JOR", new Country("Jordania", "JO", "JOR", 962));
            put("KAZ", new Country("Kazajistán", "KZ", "KAZ", 7));
            put("KEN", new Country("Kenia", "KE", "KEN", 254));
            put("KGZ", new Country("Kirguistán", "KG", "KGZ", 996));
            put("KIR", new Country("Kiribati", "KI", "KIR", 686));
            put("KWT", new Country("Kuwait", "KW", "KWT", 965));
            put("LBN", new Country("Líbano", "LB", "LBN", 961));
            put("LAO", new Country("Laos", "LA", "LAO", 856));
            put("LSO", new Country("Lesoto", "LS", "LSO", 266));
            put("LVA", new Country("Letonia", "LV", "LVA", 371));
            put("LBR", new Country("Liberia", "LR", "LBR", 231));
            put("LBY", new Country("Libia", "LY", "LBY", 218));
            put("LIE", new Country("Liechtenstein", "LI", "LIE", 423));
            put("LTU", new Country("Lituania", "LT", "LTU", 370));
            put("LUX", new Country("Luxemburgo", "LU", "LUX", 352));
            put("MEX", new Country("México", "MX", "MEX", 52));
            put("MCO", new Country("Mónaco", "MC", "MCO", 377));
            put("MAC", new Country("Macao", "MO", "MAC", 853));
            put("MKD", new Country("Macedônia", "MK", "MKD", 389));
            put("MDG", new Country("Madagascar", "MG", "MDG", 261));
            put("MYS", new Country("Malasia", "MY", "MYS", 60));
            put("MWI", new Country("Malawi", "MW", "MWI", 265));
            put("MLI", new Country("Mali", "ML", "MLI", 223));
            put("MLT", new Country("Malta", "MT", "MLT", 356));
            put("MAR", new Country("Marruecos", "MA", "MAR", 212));
            put("MTQ", new Country("Martinica", "MQ", "MTQ", 596));
            put("MUS", new Country("Mauricio", "MU", "MUS", 230));
            put("MRT", new Country("Mauritania", "MR", "MRT", 222));
            put("MYT", new Country("Mayotte", "YT", "MYT", 262));
            put("FSM", new Country("Micronesia", "FM", "FSM", 691));
            put("MDA", new Country("Moldavia", "MD", "MDA", 373));
            put("MNG", new Country("Mongolia", "MN", "MNG", 976));
            put("MNE", new Country("Montenegro", "ME", "MNE", 382));
            put("MSR", new Country("Montserrat", "MS", "MSR", 1664));
            put("MOZ", new Country("Mozambique", "MZ", "MOZ", 258));
            put("NAM", new Country("Namibia", "NA", "NAM", 264));
            put("NRU", new Country("Nauru", "NR", "NRU", 674));
            put("NPL", new Country("Nepal", "NP", "NPL", 977));
            put("NIC", new Country("Nicaragua", "NI", "NIC", 505));
            put("NER", new Country("Niger", "NE", "NER", 227));
            put("NGA", new Country("Nigeria", "NG", "NGA", 234));
            put("NIU", new Country("Niue", "NU", "NIU", 683));
            put("NOR", new Country("Noruega", "NO", "NOR", 47));
            put("NCL", new Country("Nueva Caledonia", "NC", "NCL", 687));
            put("NZL", new Country("Nueva Zelanda", "NZ", "NZL", 64));
            put("OMN", new Country("Omán", "OM", "OMN", 968));
            put("NLD", new Country("Países Bajos", "NL", "NLD", 31));
            put("PAK", new Country("Pakistán", "PK", "PAK", 92));
            put("PLW", new Country("Palau", "PW", "PLW", 680));
            put("PSE", new Country("Palestina", "PS", "PSE", 970));
            put("PAN", new Country("Panamá", "PA", "PAN", 507));
            put("PNG", new Country("Papúa Nueva Guinea", "PG", "PNG", 675));
            put("PRY", new Country("Paraguay", "PY", "PRY", 595));
            put("PER", new Country("Perú", "PE", "PER", 51));
            put("PYF", new Country("Polinesia Francesa", "PF", "PYF", 689));
            put("POL", new Country("Polonia", "PL", "POL", 48));
            put("PRT", new Country("Portugal", "PT", "PRT", 351));
            put("PRI", new Country("Puerto Rico", "PR", "PRI", 1));
            put("QAT", new Country("Qatar", "QA", "QAT", 974));
            put("GBR", new Country("Reino Unido", "GB", "GBR", 44));
            put("CAF", new Country("República Centroafricana", "CF", "CAF", 236));
            put("CZE", new Country("República Checa", "CZ", "CZE", 420));
            put("DOM", new Country("República Dominicana", "DO", "DOM", 1809));
            put("SSD", new Country("República de Sudán del Sur", "SS", "SSD", 211));
            put("REU", new Country("Reunión", "RE", "REU", 262));
            put("RWA", new Country("Ruanda", "RW", "RWA", 250));
            put("ROU", new Country("Rumanía", "RO", "ROU", 40));
            put("RUS", new Country("Rusia", "RU", "RUS", 7));
            put("ESH", new Country("Sahara Occidental", "EH", "ESH", 212));
            put("WSM", new Country("Samoa", "WS", "WSM", 685));
            put("ASM", new Country("Samoa Americana", "AS", "ASM", 1684));
            put("BLM", new Country("San Bartolomé", "BL", "BLM", 590));
            put("KNA", new Country("San Cristóbal y Nieves", "KN", "KNA", 1869));
            put("SMR", new Country("San Marino", "SM", "SMR", 378));
            put("MAF", new Country("San Martín (Francia)", "MF", "MAF", 1599));
            put("SPM", new Country("San Pedro y Miquelón", "PM", "SPM", 508));
            put("VCT", new Country("San Vicente y las Granadinas", "VC", "VCT", 1784));
            put("SHN", new Country("Santa Elena", "SH", "SHN", 290));
            put("LCA", new Country("Santa Lucía", "LC", "LCA", 1758));
            put("STP", new Country("Santo Tomé y Príncipe", "ST", "STP", 239));
            put("SEN", new Country("Senegal", "SN", "SEN", 221));
            put("SRB", new Country("Serbia", "RS", "SRB", 381));
            put("SYC", new Country("Seychelles", "SC", "SYC", 248));
            put("SLE", new Country("Sierra Leona", "SL", "SLE", 232));
            put("SGP", new Country("Singapur", "SG", "SGP", 65));
            put("SMX", new Country("Sint Maarten", "SX", "SMX", 1721));
            put("SYR", new Country("Siria", "SY", "SYR", 963));
            put("SOM", new Country("Somalia", "SO", "SOM", 252));
            put("LKA", new Country("Sri lanka", "LK", "LKA", 94));
            put("ZAF", new Country("Sudáfrica", "ZA", "ZAF", 27));
            put("SDN", new Country("Sudán", "SD", "SDN", 249));
            put("SWE", new Country("Suecia", "SE", "SWE", 46));
            put("CHE", new Country("Suiza", "CH", "CHE", 41));
            put("SUR", new Country("Surinám", "SR", "SUR", 597));
            put("SJM", new Country("Svalbard y Jan Mayen", "SJ", "SJM", 47));
            put("SWZ", new Country("Swazilandia", "SZ", "SWZ", 268));
            put("TJK", new Country("Tayikistán", "TJ", "TJK", 992));
            put("THA", new Country("Tailandia", "TH", "THA", 66));
            put("TWN", new Country("Taiwán", "TW", "TWN", 886));
            put("TZA", new Country("Tanzania", "TZ", "TZA", 255));
            put("IOT", new Country("Territorio Británico del Océano Índico", "IO", "IOT", 246));
            put("TLS", new Country("Timor Oriental", "TL", "TLS", 670));
            put("TGO", new Country("Togo", "TG", "TGO", 228));
            put("TKL", new Country("Tokelau", "TK", "TKL", 690));
            put("TON", new Country("Tonga", "TO", "TON", 676));
            put("TTO", new Country("Trinidad y Tobago", "TT", "TTO", 1868));
            put("TUN", new Country("Tunez", "TN", "TUN", 216));
            put("TKM", new Country("Turkmenistán", "TM", "TKM", 993));
            put("TUR", new Country("Turquía", "TR", "TUR", 90));
            put("TUV", new Country("Tuvalu", "TV", "TUV", 688));
            put("UKR", new Country("Ucrania", "UA", "UKR", 380));
            put("UGA", new Country("Uganda", "UG", "UGA", 256));
            put("URY", new Country("Uruguay", "UY", "URY", 598));
            put("UZB", new Country("Uzbekistán", "UZ", "UZB", 998));
            put("VUT", new Country("Vanuatu", "VU", "VUT", 678));
            put("VEN", new Country("Venezuela", "VE", "VEN", 58));
            put("VNM", new Country("Vietnam", "VN", "VNM", 84));
            put("WLF", new Country("Wallis y Futuna", "WF", "WLF", 681));
            put("YEM", new Country("Yemen", "YE", "YEM", 967));
            put("DJI", new Country("Yibuti", "DJ", "DJI", 253));
            put("ZMB", new Country("Zambia", "ZM", "ZMB", 260));
            put("ZWE", new Country("Zimbabue", "ZW", "ZWE", 263));
        }
    };

    public Phone(String number, String country) {
        this.number = number;
        setCountry(country);
    }
    
    @Pattern(regexp = "^[0-9]*$", message = "Invalid phone number")
    @Column(length = 9)
    private String number;
    
    @Column(length = 3)
    private String country;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    public String prefix;

    public String getPrefix(){
        return COUNTRIES.containsKey(country)? ("+"+COUNTRIES.get(country).getCode()) : null;
    }

    @JsonIgnore
    @Override
    public boolean isValid() {
        return !(StringUtils.isBlank(number) || StringUtils.isBlank(country) || number.length() != 9 || country.length() != 3);
    }
}
