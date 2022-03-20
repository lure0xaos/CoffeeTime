package gargoyle.ct.ui.impl

import gargoyle.ct.ex.CTException
import gargoyle.ct.log.Log
import gargoyle.ct.messages.impl.CTMessages
import gargoyle.ct.ui.CTApp
import gargoyle.ct.ui.CTDialog
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.ui.util.CTDragHelper
import gargoyle.ct.ui.util.render.GraphPaperLayout
import gargoyle.ct.ver.CTVersionInfo
import java.awt.Color
import java.awt.Cursor
import java.awt.Desktop
import java.awt.Rectangle
import java.awt.Window
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.IOException
import java.io.Serial
import java.net.URI
import java.net.URISyntaxException
import javax.swing.AbstractAction
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.SwingConstants

class CTAboutDialog(app: CTApp, owner: Window) : JDialog(owner.owner, ModalityType.APPLICATION_MODAL),
    CTDialog<Void?> {
    @Transient
    private val about: CTMessages = CTMessages(LOC_ABOUT)

    @Transient
    private val version: CTVersionInfo

    init {
        version = app.versionInfo
        init(app)
    }

    private fun addHyperlink(descriptionKey: String, content: String, address: URI, y: Int) {
        addField(descriptionKey, content, address, y)
    }

    private fun addField(descriptionKey: String, content: String?, address: URI?, y: Int) {
        val pane = contentPane
        val descriptionLabel = JLabel(String.format(FORMAT_LABEL, about.getMessage(descriptionKey)))
        adjustFontSize(descriptionLabel, FONT_SMALL)
        descriptionLabel.horizontalAlignment = SwingConstants.RIGHT
        pane.add(descriptionLabel, Rectangle(0, y, 1, 1))
        val contentLabel = address?.let { JHyperlink(content, it) } ?: JLabel(content)
        adjustFontSize(contentLabel, FONT_BIG)
        pane.add(contentLabel, Rectangle(1, y, 1, 1))
        descriptionLabel.labelFor = contentLabel
    }

    private fun adjustFontSize(component: JComponent, mul: Float) {
        val font = component.font
        component.font = font.deriveFont(font.size * mul)
    }

    @Suppress("SameParameterValue")
    private fun addHyperlink(descriptionKey: String, content: String?, address: String?, y: Int) {
        try {
            addField(descriptionKey, content, if (address == null) null else URI(address), y)
        } catch (e: URISyntaxException) {
            throw CTException(descriptionKey, e)
        }
    }

    private fun addLabel(descriptionKey: String, content: String?, y: Int) {
        addField(descriptionKey, content, null, y)
    }

    private fun init(app: CTIconProvider) {
        title = version.projectName
        setIconImage(ImageIcon(app.smallIcon).image)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        val pane = contentPane
        pane.layout = GraphPaperLayout(2, 6, 6, 3)
        addLabel(LABEL_PROJECT_NAME, version.projectName, 0)
        addLabel(LABEL_PROJECT_DESCRIPTION, version.projectDescription, 1)
        addLabel(LABEL_PROJECT_VERSION, version.projectVersion, 2)
        addLabel(LABEL_ORGANIZATION_NAME, version.organizationName, 3)
        addHyperlink(LABEL_ORGANIZATION_URL, version.organizationUrl, version.organizationUrl, 4)
        pane.add(JButton(OkAction(this)), Rectangle(0, 5, 2, 1))
        pack()
    }

    override fun showMe(): Void? {
        CTDragHelper.setLocationRelativeTo(this, owner)
        isVisible = true
        return null
    }

    private class JHyperlink(message: String?, val address: URI) :
        JLabel(String.format("<html><u>%s</u></html>", message)) {
        init {
            foreground = Color.BLUE.darker()
            cursor = Cursor(Cursor.HAND_CURSOR)
            addMouseListener(HyperlinkLabelMouseAdapter())
        }

        private inner class HyperlinkLabelMouseAdapter : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(address)
                    }
                } catch (ex: UnsupportedOperationException) {
                    Log.error(ex, ex.localizedMessage)
                } catch (ex: IOException) {
                    Log.error(ex, ex.localizedMessage)
                }
            }
        }

        companion object {
            @Serial
            private val serialVersionUID = -2543205042883642083L
        }
    }

    private class OkAction(private val dialog: CTAboutDialog) : AbstractAction("OK") {
        override fun actionPerformed(e: ActionEvent) {
            dialog.dispose()
        }

        @Throws(CloneNotSupportedException::class)
        public override fun clone(): OkAction {
            return super.clone() as OkAction
        }

        companion object {
            @Serial
            private val serialVersionUID = -4568926242772773378L
        }
    }

    companion object {
        private const val FONT_BIG = 1.2f
        private const val FONT_SMALL = 0.8f
        private const val FORMAT_LABEL = "%s:"
        private const val LABEL_ORGANIZATION_NAME = "label_organization_name"
        private const val LABEL_ORGANIZATION_URL = "label_organization_url"
        private const val LABEL_PROJECT_DESCRIPTION = "label_project_description"
        private const val LABEL_PROJECT_NAME = "label_project_name"
        private const val LABEL_PROJECT_VERSION = "label_project_version"
        private const val LOC_ABOUT = "messages/about"

        @Serial
        private val serialVersionUID = 4721343233167467386L
    }
}
