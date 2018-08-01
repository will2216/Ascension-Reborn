package ascensionMod.relics;

import basemod.abstracts.CustomRelic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
 

public class StarOfAscension extends CustomRelic {
	public static final Logger logger = LogManager.getLogger(StarOfAscension.class.getName());
	
	public static final String ID = "AscMod:StarOfAscension";
    private static final String IMG = "relics/StarOfAscension.png";
    private static final String OutlineIMG = "relics/outline/StarOfAscension.png";
    

    public void onEquip() {
    	if(CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel > 15) {
    		com.megacrit.cardcrawl.shop.ShopScreen.purgeCost = 100;
        	com.megacrit.cardcrawl.shop.ShopScreen.actualPurgeCost = 100;
    	}
    }
    
    
    public int onPlayerHeal(int healAmount) {
    	if(CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel >= 19) {
    		flash();
    	}
    	
    	return healAmount;
    }
    
    public StarOfAscension() {
        super(ID, new Texture(IMG), new Texture(OutlineIMG), RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new StarOfAscension();
    }
}