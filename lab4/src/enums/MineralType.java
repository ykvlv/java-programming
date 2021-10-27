package enums;



public enum MineralType {
    MOONROCK("Лунный камень"),
    ROCKCRYSTAL("Горный хрусталь"),
    FELDSPAR("Полевой шпат"),
    MICA("Слюда"),
    IRONSTONE("Бурый железняк"),
    COPPERPYRITE("Медный колчедан"),
    SULFUR("Сера"),
    PYRITE("Пирит"),
    CHALCOPYRITE("Халькопирит"),
    ZINKBLENDE("Цинковая обманка"),
    GALENA("Свинцовый блеск"),
    MAGNETICIRONORE("Магнитный железняк"),
    DIAMOND("Алмаз"),
    APATITE("Апатит"),
    HALITE("Галит");

    MineralType(String translation){
        this.translation = translation;
    }

    private final String translation;

    public String translation() {
        return this.translation;
    }
}