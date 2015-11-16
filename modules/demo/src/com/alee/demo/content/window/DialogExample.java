/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.demo.content.window;

import com.alee.demo.DemoApplication;
import com.alee.demo.api.*;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.managers.style.StyleId;
import com.alee.utils.CollectionUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Mikle Garin
 */

public class DialogExample extends AbstractExample
{
    @Override
    public String getId ()
    {
        return "dialog";
    }

    @Override
    protected String getStyleFileName ()
    {
        return "rootpane";
    }

    @Override
    public FeatureType getFeatureType ()
    {
        return FeatureType.swing;
    }

    @Override
    protected List<Preview> createPreviews ()
    {
        final DialogPreview e1 = new DialogPreview ( "common", StyleId.dialog );
        final DialogPreview e2 = new DialogPreview ( "decorated", StyleId.dialogDecorated );
        return CollectionUtils.<Preview>asList ( e1, e2 );
    }

    /**
     * Simple dialog preview.
     */
    protected class DialogPreview extends AbstractStylePreview
    {
        /**
         * Constructs new style preview.
         *
         * @param id      preview ID
         * @param styleId preview style ID
         */
        public DialogPreview ( final String id, final StyleId styleId )
        {
            super ( DialogExample.this, id, FeatureState.updated, styleId );
        }

        @Override
        protected List<? extends JComponent> createPreviewElements ( final StyleId id )
        {
            final WebButton showFrame = new WebButton ( getExampleLanguagePrefix () + "show", loadIcon ( "dialog/" + getId () + ".png" ) );
            showFrame.addActionListener ( new ActionListener ()
            {
                @Override
                public void actionPerformed ( final ActionEvent e )
                {
                    final String title = getExampleLanguagePrefix () + "content";
                    final WebDialog dialog = new WebDialog ( getStyleId (), showFrame, title );
                    dialog.setIconImages ( WebLookAndFeel.getImages () );
                    dialog.add ( new WebLabel ( title, WebLabel.CENTER ) );
                    dialog.setSize ( 500, 400 );
                    dialog.setLocationRelativeTo ( DemoApplication.getInstance () );
                    dialog.setVisible ( true );
                }
            } );
            return CollectionUtils.asList ( showFrame );
        }
    }
}