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

package com.alee.painter.decoration.layout;

import com.alee.api.data.BoxOrientation;
import com.alee.painter.decoration.IDecoration;
import com.alee.utils.SwingUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract implementation of simple icon and text layout.
 * It only paints contents placed under {@link #ICON} and {@link #TEXT} constraints.
 *
 * @param <E> component type
 * @param <D> decoration type
 * @param <I> layout type
 * @author Mikle Garin
 */

@XStreamAlias ( "IconTextLayout" )
public class IconTextContentLayout<E extends JComponent, D extends IDecoration<E, D>, I extends IconTextContentLayout<E, D, I>>
        extends AbstractContentLayout<E, D, I> implements SwingConstants
{
    /**
     * Layout constraints.
     */
    public static final String ICON = "icon";
    public static final String TEXT = "text";

    /**
     * Gap between icon and text contents.
     */
    @XStreamAsAttribute
    protected Integer gap;

    /**
     * Horizontal content alignment.
     */
    @XStreamAsAttribute
    protected BoxOrientation halign;

    /**
     * Vertical content alignment.
     */
    @XStreamAsAttribute
    protected BoxOrientation valign;

    /**
     * Horizontal text position.
     */
    @XStreamAsAttribute
    protected BoxOrientation hpos;

    /**
     * Vertical text position.
     */
    @XStreamAsAttribute
    protected BoxOrientation vpos;

    /**
     * Returns gap between icon and text contents.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return gap between icon and text contents
     */
    protected int getIconTextGap ( final E c, final D d )
    {
        return gap != null ? gap : 0;
    }

    /**
     * Returns horizontal content alignment.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return horizontal content alignment
     */
    protected int getHorizontalAlignment ( final E c, final D d )
    {
        return halign != null ? halign.getValue () : CENTER;
    }

    /**
     * Returns vertical content alignment.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return vertical content alignment
     */
    protected int getVerticalAlignment ( final E c, final D d )
    {
        return valign != null ? valign.getValue () : CENTER;
    }

    /**
     * Returns horizontal text position.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return horizontal text position
     */
    protected int getHorizontalTextPosition ( final E c, final D d )
    {
        return hpos != null ? hpos.getValue () : TRAILING;
    }

    /**
     * Returns vertical text position.
     *
     * @param c painted component
     * @param d painted decoration state
     * @return vertical text position
     */
    protected int getVerticalTextPosition ( final E c, final D d )
    {
        return vpos != null ? vpos.getValue () : CENTER;
    }

    @Override
    protected void paintContent ( final Graphics2D g2d, final Rectangle bounds, final E c, final D d )
    {
        // Calculating available size
        final Dimension size = getContentPreferredSize ( c, d, bounds.getSize () );
        size.width = Math.min ( size.width, bounds.width );
        size.height = Math.min ( size.height, bounds.height );

        // Painting contents if at least some space is available
        if ( size.width > 0 && size.height > 0 )
        {
            // Calculating smallest content bounds
            final boolean ltr = c.getComponentOrientation ().isLeftToRight ();
            final int halign = getHorizontalAlignment ( c, d );
            final int valign = getVerticalAlignment ( c, d );
            final Rectangle b = new Rectangle ( 0, 0, size.width, size.height );
            if ( halign == LEFT || halign == LEADING && ltr )
            {
                b.x = bounds.x;
            }
            else if ( halign == CENTER )
            {
                b.x = bounds.x + bounds.width / 2 - size.width / 2;
            }
            else
            {
                b.x = bounds.x + bounds.width - size.width;
            }
            if ( valign == TOP )
            {
                b.y = bounds.y;
            }
            else if ( valign == CENTER )
            {
                b.y = bounds.y + bounds.height / 2 - size.height / 2;
            }
            else
            {
                b.y = bounds.y + bounds.height - size.height;
            }

            // Painting contents
            final boolean hasIcon = !isEmpty ( c, d, ICON );
            final boolean hasText = !isEmpty ( c, d, TEXT );
            if ( hasIcon && hasText )
            {
                final int hpos = getHorizontalTextPosition ( c, d );
                final int vpos = getVerticalTextPosition ( c, d );
                if ( hpos != CENTER || vpos != CENTER )
                {
                    final Dimension ips = getPreferredSize ( c, d, bounds.getSize (), ICON );
                    final int gap = getIconTextGap ( c, d );
                    if ( hpos == RIGHT || hpos == TRAILING && ltr )
                    {
                        paint ( g2d, new Rectangle ( b.x, b.y, ips.width, b.height ), c, d, ICON );
                        paint ( g2d, new Rectangle ( b.x + gap + ips.width, b.y, b.width - ips.width - gap, b.height ), c, d, TEXT );
                    }
                    else if ( hpos == CENTER )
                    {
                        if ( vpos == TOP )
                        {
                            paint ( g2d, new Rectangle ( b.x, b.y, b.width, b.height - gap - ips.height ), c, d, TEXT );
                            paint ( g2d, new Rectangle ( b.x, b.y + b.height - ips.height, b.width, ips.height ), c, d, ICON );
                        }
                        else
                        {
                            paint ( g2d, new Rectangle ( b.x, b.y, b.width, ips.height ), c, d, ICON );
                            paint ( g2d, new Rectangle ( b.x, b.y + ips.height + gap, b.width, b.height - ips.height - gap ), c, d, TEXT );
                        }
                    }
                    else
                    {
                        paint ( g2d, new Rectangle ( b.x + b.width - ips.width, b.y, ips.width, b.height ), c, d, ICON );
                        paint ( g2d, new Rectangle ( b.x, b.y, b.width - ips.width - gap, b.height ), c, d, TEXT );
                    }
                }
                else
                {
                    paint ( g2d, b, c, d, ICON );
                    paint ( g2d, b, c, d, TEXT );
                }
            }
            else if ( hasIcon )
            {
                paint ( g2d, b, c, d, ICON );
            }
            else if ( hasText )
            {
                paint ( g2d, b, c, d, TEXT );
            }
        }
    }

    @Override
    protected Dimension getContentPreferredSize ( final E c, final D d, final Dimension available )
    {
        final boolean hasIcon = !isEmpty ( c, d, ICON );
        final boolean hasText = !isEmpty ( c, d, TEXT );
        if ( hasIcon && hasText )
        {
            final int hpos = getHorizontalTextPosition ( c, d );
            final int vpos = getVerticalTextPosition ( c, d );
            final Dimension ips = getPreferredSize ( c, d, available, ICON );
            if ( hpos != CENTER || vpos != CENTER )
            {
                final int gap = getIconTextGap ( c, d );
                if ( hpos == LEFT || hpos == LEADING || hpos == RIGHT || hpos == TRAILING )
                {
                    final Dimension havailable = new Dimension ( available.width - gap - ips.width, available.height );
                    final Dimension cps = getPreferredSize ( c, d, havailable, TEXT );
                    return new Dimension ( ips.width + gap + cps.width, Math.max ( ips.height, cps.height ) );
                }
                else
                {
                    final Dimension vavailable = new Dimension ( available.width, available.height - gap - ips.height );
                    final Dimension cps = getPreferredSize ( c, d, vavailable, TEXT );
                    return new Dimension ( Math.max ( ips.width, cps.width ), ips.height + gap + cps.height );
                }
            }
            else
            {
                return SwingUtils.max ( ips, getPreferredSize ( c, d, available, TEXT ) );
            }
        }
        else if ( hasIcon )
        {
            return getPreferredSize ( c, d, available, ICON );
        }
        else if ( hasText )
        {
            return getPreferredSize ( c, d, available, TEXT );
        }
        else
        {
            return new Dimension ( 0, 0 );
        }
    }

    @Override
    public I merge ( final I layout )
    {
        super.merge ( layout );
        gap = layout.isOverwrite () || layout.gap != null ? layout.gap : gap;
        valign = layout.isOverwrite () || layout.valign != null ? layout.valign : valign;
        halign = layout.isOverwrite () || layout.halign != null ? layout.halign : halign;
        hpos = layout.isOverwrite () || layout.hpos != null ? layout.hpos : hpos;
        vpos = layout.isOverwrite () || layout.vpos != null ? layout.vpos : vpos;
        return ( I ) this;
    }
}