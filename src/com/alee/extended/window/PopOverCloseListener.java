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

package com.alee.extended.window;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Mikle Garin
 */

public abstract class PopOverCloseListener implements ComponentListener, WindowListener
{
    @Override
    public void componentResized ( final ComponentEvent e )
    {
        // Do nothing
    }

    @Override
    public void componentMoved ( final ComponentEvent e )
    {
        // Do nothing
    }

    @Override
    public void componentShown ( final ComponentEvent e )
    {
        // Do nothing
    }

    @Override
    public void componentHidden ( final ComponentEvent e )
    {
        popOverClosed ();
    }

    @Override
    public void windowOpened ( final WindowEvent e )
    {
        // Do nothing
    }

    @Override
    public void windowClosing ( final WindowEvent e )
    {
        // Do nothing
    }

    @Override
    public void windowClosed ( final WindowEvent e )
    {
        popOverClosed ();
    }

    @Override
    public void windowIconified ( final WindowEvent e )
    {
        // Do nothing
    }

    @Override
    public void windowDeiconified ( final WindowEvent e )
    {
        // Do nothing
    }

    @Override
    public void windowActivated ( final WindowEvent e )
    {
        // Do nothing
    }

    @Override
    public void windowDeactivated ( final WindowEvent e )
    {
        // Do nothing
    }

    public abstract void popOverClosed ();
}