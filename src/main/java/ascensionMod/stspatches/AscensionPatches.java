package ascensionMod.stspatches;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import basemod.BaseMod;
import basemod.ReflectionHacks;

import ascensionMod.AscensionPlusMod;


public class AscensionPatches 
{
	public static final Logger logger = LogManager.getLogger(AscensionPatches.class.getName());
	
	  
	
	@SuppressWarnings("unchecked")
	private static Map<String, UIStrings> UiString = (Map<String, UIStrings>)BaseMod.gson.fromJson(loadJson("localization/eng/AscensionDesc.json"), getTrueType(UIStrings.class));
	
	private static UIStrings AscensionLevelStrings = UiString.get("AscensionModeDescriptions");
	
	private static String[] AscensionLevels = AscensionLevelStrings.TEXT;
	

	
	private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }
	
	private static Type getTrueType(Type type)
	{
		 @SuppressWarnings("rawtypes")
		HashMap TypeTokens = (HashMap)ReflectionHacks.getPrivateStatic(BaseMod.class, "typeTokens");
		 return (Type)TypeTokens.get(type);
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "updateHitbox",
		paramtypes = {}
	)
	public static class HitboxPatch {

		@SuppressWarnings("rawtypes")
		@SpireInsertPatch(
			rloc=62
		)
		public static SpireReturn Insert(CharacterOption __instance)
		{
			return SpireReturn.Return(null);
		}
		
		
		public static void Postfix(CharacterOption __instance) {
			if(CardCrawlGame.chosenCharacter == AbstractPlayer.PlayerClass.THE_SILENT) {
				if(AscensionPlusMod.AbsoluteAscensionLevel > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"))) {
					AscensionPlusMod.AbsoluteAscensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"));
				}
			}
			else if(CardCrawlGame.chosenCharacter == AbstractPlayer.PlayerClass.IRONCLAD) {
				if(AscensionPlusMod.AbsoluteAscensionLevel > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"))) {
					AscensionPlusMod.AbsoluteAscensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"));
				}
			}
			else if(CardCrawlGame.chosenCharacter == AbstractPlayer.PlayerClass.DEFECT) {
				if(AscensionPlusMod.AbsoluteAscensionLevel > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"))) {
					AscensionPlusMod.AbsoluteAscensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"));
				}
			}
			else {
				if(AscensionPlusMod.AbsoluteAscensionLevel > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"))) {
					AscensionPlusMod.AbsoluteAscensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"));
				}
			}
			
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = AscensionPlusMod.AbsoluteAscensionLevel;
           	if(AscensionPlusMod.AbsoluteAscensionLevel <= 20) {
           		if (21 < AscensionPlusMod.AbsoluteAscensionLevel) {
           			if(__instance.c == AbstractPlayer.PlayerClass.THE_SILENT) {
           				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"));
           				AscensionPlusMod.AbsoluteAscensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
           			}
           			else if(__instance.c == AbstractPlayer.PlayerClass.IRONCLAD) {
           				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"));
           				AscensionPlusMod.AbsoluteAscensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
           			}
           			else if(__instance.c == AbstractPlayer.PlayerClass.DEFECT) {
           				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"));
           				AscensionPlusMod.AbsoluteAscensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
           			}
           			else {
           				CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"));
           				AscensionPlusMod.AbsoluteAscensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
           			}
           		}
           	}
           	if (0 > CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel) {
           		CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = 0;
           		AscensionPlusMod.AbsoluteAscensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
           	}
		
           	int ascensionLevel = AscensionPlusMod.AbsoluteAscensionLevel;
						
           	if ((ascensionLevel > 0) && !(ascensionLevel < 0)) {
           		CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(ascensionLevel - 1)];
           	}
           	else {
           		CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = "";	
           	}
        }
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "decrementAscensionLevel",
		paramtypes = {"int"}
	)
	public static class decrementPatch {
		public static SpireReturn<?> Prefix(CharacterOption __instance, int level) {
			if(level < 0) {
				level = 0;
			}
			
			CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
			AscensionPlusMod.AbsoluteAscensionLevel = level;
			
			if(level >= 20) {
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(level - 1)];
				return SpireReturn.Return(null);
			}
			else if(level == 0) {
				return SpireReturn.Return(null);
			}
			else {
				return SpireReturn.Continue();
			}
		}
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.charSelect.CharacterOption",
		method = "incrementAscensionLevel",
		paramtypes = {"int"}
	)
	public static class incrementPatch {
		public static SpireReturn<?> Prefix(CharacterOption __instance, int level) {		
			if(__instance.c == AbstractPlayer.PlayerClass.THE_SILENT) {
				if(level > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"))) {
					level = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"));
				}
			}
			else if(__instance.c == AbstractPlayer.PlayerClass.IRONCLAD) {
				if(level > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"))) {
					level = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"));
				}
			}
			else if(__instance.c == AbstractPlayer.PlayerClass.DEFECT) {
				if(level > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"))) {
					level = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"));
				}
			}
			else {
				if(level > Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"))) {
					level = Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"));
				}
			}
			
			CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
			AscensionPlusMod.AbsoluteAscensionLevel = level;
			
			if(level >= 20) {
				
				CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(level - 1)];
				return SpireReturn.Return(null);
			}
			else {
				return SpireReturn.Continue();
			}
		}
	}
	
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.custom.CustomModeScreen",
		method = "renderAscension"
	)
	public static class RenderPatch{

		@SpireInsertPatch(
			rloc=61
		)
		public static void Insert(CustomModeScreen __instance, SpriteBatch sb)
		{
			AscensionPlusMod.AbsoluteAscensionLevel = __instance.ascensionLevel;
			__instance.ascensionLevel = 0;
		}
		
		public static void Postfix(CustomModeScreen __instance, SpriteBatch sb) {
			if(AscensionPlusMod.AbsoluteAscensionLevel > 25) {
				AscensionPlusMod.AbsoluteAscensionLevel = 25;
			}
			if(AscensionPlusMod.AbsoluteAscensionLevel < 0) {
				AscensionPlusMod.AbsoluteAscensionLevel = 0;
			}
			
			__instance.ascensionLevel = AscensionPlusMod.AbsoluteAscensionLevel;

			
			if(__instance.ascensionLevel != 0) {
				Field screenXF = null;
				try {
					screenXF = CustomModeScreen.class.getDeclaredField("screenX");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				screenXF.setAccessible(true);
				float screenX = 0;
				try {
					screenX = (float)screenXF.get(__instance);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Field ascensionModeHbF = null;
				try {
					ascensionModeHbF = CustomModeScreen.class.getDeclaredField("ascensionModeHb");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ascensionModeHbF.setAccessible(true);
				Hitbox ascensionModeHb = null;
				try {
					ascensionModeHb = (Hitbox)ascensionModeHbF.get(__instance);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				FontHelper.renderSmartText(sb, FontHelper.charDescFont, CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = AscensionLevels[(__instance.ascensionLevel - 1)], screenX + 475.0F * com.megacrit.cardcrawl.core.Settings.scale, ascensionModeHb.cY + 10.0F * com.megacrit.cardcrawl.core.Settings.scale, 9999.0F, 32.0F * com.megacrit.cardcrawl.core.Settings.scale, com.megacrit.cardcrawl.core.Settings.CREAM_COLOR);
			}
		}
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.ui.panels.TopPanel",
		method = "setupAscensionMode",
		paramtypes = {}
	)
	public static class SetupPatch{
		@SuppressWarnings("rawtypes")
		@SpireInsertPatch(
			rloc=5
		)
		public static SpireReturn Insert(TopPanel __instance)
		{
		    return SpireReturn.Return(null);
		}
		public static void Postfix(TopPanel __instance) {
			if (AbstractDungeon.isAscensionMode) {
				final StringBuilder sb = new StringBuilder();
				for (int i = 0; i < AbstractDungeon.ascensionLevel; ++i) {
					sb.append(AscensionLevels[i]);
					System.out.println(AscensionLevels[i]);
					if (i != AbstractDungeon.ascensionLevel - 1) {
						sb.append(" NL ");
					}
				}
				Field ascensionStringF = null;
				try {
					ascensionStringF = TopPanel.class.getDeclaredField("ascensionString");
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(sb.toString());
				ascensionStringF.setAccessible(true);
				//String x = sb.toString();
				try {
					ascensionStringF.set(__instance, sb.toString());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        }
	}
	
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.DeathScreen",
		method = "update",
		paramtypes = {}
	)
	public static class UpdatePatch {
		public static void Postfix(DeathScreen __instance) {
			if ((__instance.isVictory) && (AbstractDungeon.isAscensionMode) && (!Settings.isTrial) && (AbstractDungeon.ascensionLevel < 25)) {
				if(AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
					if(AbstractDungeon.ascensionLevel == Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT"))) {
						AscensionPlusMod.config.setString("MaxAscLvl_SILENT", ("" + (1 + Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_SILENT")))));
						try {
							AscensionPlusMod.config.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if(AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.IRONCLAD) {
					if(AbstractDungeon.ascensionLevel == Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD"))) {
						AscensionPlusMod.config.setString("MaxAscLvl_IRONCLAD", ("" + (1 + Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_IRONCLAD")))));
						try {
							AscensionPlusMod.config.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if(AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.DEFECT) {
					if(AbstractDungeon.ascensionLevel == Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT"))) {
						AscensionPlusMod.config.setString("MaxAscLvl_DEFECT", ("" + (1 + Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl_DEFECT")))));
						try {
							AscensionPlusMod.config.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else {
					if(AbstractDungeon.ascensionLevel == Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl"))) {
						AscensionPlusMod.config.setString("MaxAscLvl", ("" + (1 + Integer.parseInt(AscensionPlusMod.config.getString("MaxAscLvl")))));
						try {
							AscensionPlusMod.config.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	@SpirePatch(
		cls = "com.megacrit.cardcrawl.screens.custom.CustomModeScreen",
		method = "updateAscension",
		paramtypes = {}
	)
	public static class UpdateAscPatch {
		public static void Prefix(CustomModeScreen __instance) {
			
			Field ascRightHbF = null;
			try {
				ascRightHbF = CustomModeScreen.class.getDeclaredField("ascRightHb");
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ascRightHbF.setAccessible(true);
			Hitbox ascRightHb = null;
			try {
				ascRightHb = (Hitbox)ascRightHbF.get(__instance);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ascRightHb.update();
			if ((ascRightHb.clicked) || (CInputActionSet.pageRightViewExhaust.isJustPressed())) {
				//playClickFinishSound();
				
				ascRightHb.clicked = false;
				__instance.ascensionLevel += 1;
				if (__instance.ascensionLevel > 25) {
					__instance.ascensionLevel = 25;
				}
				
				__instance.isAscensionMode = true;
			}
			AscensionPlusMod.AbsoluteAscensionLevel = __instance.ascensionLevel;
		}
	}
}
