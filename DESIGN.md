---
name: "Penny"
description: "A luminous, local-first Android ledger shaped by aurora glass and fast financial legibility."
colors:
  aurora-violet: "#7547FF"
  aurora-magenta: "#E54BBE"
  aurora-cyan: "#34D9EA"
  money-green-light: "#0A8F68"
  money-green-dark: "#70E3BC"
  light-primary: "#6540D7"
  light-primary-container: "#EDE6FF"
  light-secondary: "#9A347C"
  light-secondary-container: "#FFD8EF"
  light-tertiary: "#006875"
  light-tertiary-container: "#9CF0FF"
  light-background: "#F8F6FF"
  light-surface: "#FCF9FF"
  light-surface-muted: "#EAE5F0"
  light-ink: "#1C1822"
  light-ink-muted: "#645E6C"
  light-divider: "#D0C8D7"
  light-error: "#BA1A1A"
  dark-primary: "#CDBDFF"
  dark-primary-container: "#4D28A5"
  dark-secondary: "#FFACE0"
  dark-secondary-container: "#7B2864"
  dark-tertiary: "#4ED8EA"
  dark-tertiary-container: "#004E59"
  dark-background: "#0D0914"
  dark-surface: "#14101C"
  dark-surface-muted: "#332D3B"
  dark-ink: "#EAE2EF"
  dark-ink-muted: "#CEC5D2"
  dark-divider: "#49414F"
  dark-error: "#FFB4AB"
typography:
  display-large:
    fontFamily: "sans-serif"
    fontSize: "52sp"
    fontWeight: 700
    lineHeight: "56sp"
    letterSpacing: "-1.2sp"
  display-medium:
    fontFamily: "sans-serif"
    fontSize: "40sp"
    fontWeight: 600
    lineHeight: "44sp"
    letterSpacing: "-0.8sp"
  headline-large:
    fontFamily: "sans-serif"
    fontSize: "32sp"
    fontWeight: 700
    lineHeight: "38sp"
    letterSpacing: "-0.5sp"
  headline-medium:
    fontFamily: "sans-serif"
    fontSize: "26sp"
    fontWeight: 600
    lineHeight: "32sp"
    letterSpacing: "-0.3sp"
  title-large:
    fontFamily: "sans-serif"
    fontSize: "22sp"
    fontWeight: 600
    lineHeight: "28sp"
  title-medium:
    fontFamily: "sans-serif"
    fontSize: "16sp"
    fontWeight: 600
    lineHeight: "22sp"
  body-large:
    fontFamily: "sans-serif"
    fontSize: "16sp"
    fontWeight: 400
    lineHeight: "24sp"
  body-medium:
    fontFamily: "sans-serif"
    fontSize: "14sp"
    fontWeight: 400
    lineHeight: "20sp"
  body-small:
    fontFamily: "sans-serif"
    fontSize: "12sp"
    fontWeight: 400
    lineHeight: "17sp"
  label-large:
    fontFamily: "sans-serif"
    fontSize: "14sp"
    fontWeight: 600
    lineHeight: "20sp"
  label-medium:
    fontFamily: "sans-serif"
    fontSize: "12sp"
    fontWeight: 500
    lineHeight: "16sp"
  label-small:
    fontFamily: "sans-serif"
    fontSize: "11sp"
    fontWeight: 500
    lineHeight: "14sp"
rounded:
  xs: "8dp"
  sm: "12dp"
  md: "16dp"
  lg: "24dp"
  xl: "32dp"
spacing:
  xs: "8dp"
  sm: "12dp"
  md: "16dp"
  panel: "18dp"
  gutter: "20dp"
  lg: "24dp"
  xl: "32dp"
components:
  button-primary:
    backgroundColor: "{colors.light-primary}"
    textColor: "#FFFFFF"
    typography: "{typography.label-large}"
    rounded: "18dp"
    height: "54dp"
    width: "100%"
  glass-panel-light:
    backgroundColor: "rgba(252, 249, 255, 0.82)"
    textColor: "{colors.light-ink}"
    rounded: "{rounded.lg}"
    padding: "{spacing.panel}"
  glass-panel-dark:
    backgroundColor: "rgba(20, 16, 28, 0.82)"
    textColor: "{colors.dark-ink}"
    rounded: "{rounded.lg}"
    padding: "{spacing.panel}"
  balance-lobe:
    backgroundColor: "linear-gradient(135deg, #4221B8, #6534C7, #A42D7E)"
    textColor: "#FFFFFF"
    rounded: "38dp 24dp 42dp 24dp"
    padding: "{spacing.lg}"
    height: "252dp"
    width: "100%"
  transaction-icon:
    backgroundColor: "{colors.light-primary-container}"
    textColor: "{colors.light-primary}"
    rounded: "15dp"
    size: "46dp"
  theme-choice-selected:
    backgroundColor: "{colors.light-primary-container}"
    textColor: "{colors.light-primary}"
    typography: "{typography.label-medium}"
    rounded: "18dp"
    height: "76dp"
---

# Design System: Penny

## Overview

**Creative North Star: "Liquid Ledger"**

Penny is an Aurora Maximalist native Android finance interface: private money movement is viewed through a single luminous field of violet, magenta, and cyan rather than through a generic stack of equal cards. A cool white or violet-black ground keeps everyday ledger content calm while one dominant liquid balance lobe gives the home screen its unmistakable silhouette.

The system is expressive at the level of atmosphere, material, and motion, then disciplined at the level of reading and control. Glass surfaces are translucent but legible, type is crisp and Android-native, and semantic states remain clear in light, dark, and system-selected themes. Spring state selection and a traveling highlight make balance exploration feel physical without turning routine financial work into spectacle.

**Key Characteristics:**

- Aurora violet, magenta, and cyan establish identity; semantic Material roles carry the rest of the interface.
- A dominant asymmetric balance lobe anchors the hierarchy.
- Translucent glass panels group related information without creating a wall of equal cards.
- Compact capsules, generous touch targets, and Material behavior keep the experience native.
- Light, dark, and system themes are equal expressions of the same visual world.

## Colors

The palette moves from cool paper to violet-black, with a saturated aurora reserved for identity, focus, and the signature balance object.

### Primary

- **Aurora Violet:** The recognizable Penny signal, used in the brand orb, ambient glow, and gradient-led signature moments.
- **Ledger Violet:** The interactive primary role for actions, pending states, active navigation, and selected controls in light mode; its luminous counterpart carries the same role in dark mode.
- **Violet Container:** The quiet selection and icon-field color used behind active or actionable content.

### Secondary

- **Aurora Magenta:** Warms the aurora and supplies the balance lobe's final color movement; it is not a general-purpose status color.
- **Ledger Magenta:** Supports secondary Material roles and permission emphasis without competing with the primary violet.

### Tertiary

- **Aurora Cyan:** Adds cool depth to glows, the brand gradient, and positive snackbar confirmation.
- **Ledger Cyan:** Carries tertiary Material roles in both themes and stays distinct from financial credit green.

### Neutral

- **Cool Paper / Violet Black:** The paired background roles keep light mode airy and dark mode genuinely deep rather than inverted gray.
- **Glass Surface:** Near-white or near-black surfaces become translucent when used as panels, sheets, navigation, and snackbars.
- **Primary Ink:** High-contrast reading color for headings, amounts, and transactional content.
- **Muted Ink:** Secondary copy, timestamps, account details, and metadata.
- **Soft Divider:** Low-contrast separation inside shared glass containers; avoid outlining every surface.
- **Money Green:** Reserved for incoming money and positive financial values, with separate light- and dark-theme values.
- **Error Red:** Reserved for destructive and error states such as local data deletion.

### Named Rules

**The Aurora Is Identity Rule.** Use the three-color aurora for branded atmosphere and signature objects, not as a rainbow applied to every component.

**The Money Green Rule.** Green means incoming value; do not use it as a generic success accent when cyan or primary violet can communicate the state.

**The Paired Theme Rule.** Resolve semantic roles through the complete light or dark scheme. Never invert colors mechanically or mix roles from opposing schemes.

## Typography

**Display Font:** Android Sans Serif
**Body Font:** Android Sans Serif
**Label Font:** Android Sans Serif

**Character:** The typography is crisp, direct, and numerical without becoming clinical. Strong display weights give balances and screen titles authority; compact labels preserve scan speed around dense transactional metadata.

### Hierarchy

- **Display Large:** Bold, tightly tracked type for exceptional numerical emphasis.
- **Display Medium:** Semibold display type for the balance amount, onboarding statement, and transaction-sheet amount.
- **Headline Large:** Bold screen and product titles.
- **Headline Medium:** Semibold detail-screen titles.
- **Title Large:** Semibold section headings and prominent supporting values.
- **Title Medium:** Semibold transaction names, setting labels, and component titles.
- **Body Large:** Long-form explanatory copy and primary descriptions.
- **Body Medium:** Standard supporting content, dates, and contextual descriptions.
- **Body Small:** Dense metadata, helper copy, and secondary transaction details.
- **Labels:** Semibold or medium-weight control, capsule, and compact status text.

### Named Rules

**The Amount Leads Rule.** On money-focused surfaces, the monetary value receives the strongest available type role; labels explain it without competing.

**The Scale, Don't Improvise Rule.** Use the defined Material type roles in scalable `sp`; do not introduce one-off text sizes inside screens.

## Layout

Penny is currently a compact phone experience. Primary scrolling screens use a 20 dp horizontal gutter and compact 14–18 dp vertical rhythm; panels generally use 16–18 dp internal padding. Content is edge-to-edge at the window level, then protected with status, navigation, cutout, and IME insets where needed.

The first viewport establishes a clear hierarchy: a quiet brand-and-date header, then the 252 dp-tall balance lobe, followed by permissions, pending work, and the transaction timeline. The header carries no decorative action; live capture status belongs inside the lobe. Bottom navigation floats inside 16 dp side margins and navigation-bar insets. Lists consolidate related rows inside a shared panel rather than turning every transaction into a separate card.

Phone is the only resolved size class. Do not stretch this composition onto tablets; expanded layouts require an intentional navigation rail or drawer and a new content arrangement.

## Elevation & Depth

Depth is a hybrid of Material tonal elevation, translucency, ambient aurora fields, and a few deliberate shadows. Most glass panels use a translucent surface plus low tonal elevation. The signature balance lobe, brand orb, floating navigation, and snackbar receive broader shadows because they sit above the ledger plane.

### Shadow Vocabulary

- **Panel Tone:** A 2 dp tonal elevation for ordinary glass panels; structure comes from tone, not a visible outline.
- **Floating Navigation:** A 20 dp shadow around the 28 dp navigation capsule.
- **Balance Aura:** A 24 dp colored shadow using violet ambient light and magenta spot light.
- **Brand Orb:** An 18 dp colored shadow that extends the orb's three-color gradient.
- **Snackbar Lift:** A 22 dp shadow under the floating confirmation capsule.

### Named Rules

**The Glass Must Read Rule.** Translucency adds atmosphere only after foreground contrast is secure in both themes.

**The Signature Earns Shadow Rule.** Broad colored shadows belong to the lobe and brand moments; routine panels stay tonally layered.

## Shapes

The base shape scale progresses from 8 dp to 32 dp, moving from compact controls to large containers. Ordinary panels use a generous 24 dp radius, floating navigation uses 28 dp, and primary full-width buttons use 18 dp. Icons often sit in circles or softly squared 13–15 dp fields.

The balance lobe is the purposeful exception: its corners are asymmetric, producing a swollen coastline rather than a generic rounded rectangle. Keep this irregular geometry exclusive to signature financial summaries.

## Components

### Buttons

- **Shape:** Confident, softly rounded primary buttons use an 18 dp radius and a minimum 54 dp height.
- **Primary:** Filled with the current theme's primary role and paired with its on-primary text role; full width for decisive onboarding and save actions.
- **Focus / Disabled:** Preserve Material focus, pressed, ripple, and disabled behavior rather than replacing it with decorative custom states.
- **Secondary / Text:** Outlined buttons support secondary actions; text buttons carry low-emphasis, settings, dismissal, and destructive actions, with error color used only when destructive.

### Chips

- **Style:** Material filter chips define date-range selection; suggestion chips offer compact expense-title shortcuts.
- **State:** Selected filters use Material's semantic selected treatment. Chips remain content-sized, horizontally scrollable, and separated by 8 dp.

### Cards / Containers

- **Corner Style:** Glass panels use the large 24 dp system radius.
- **Background:** The current surface role at 82% opacity.
- **Shadow Strategy:** Low tonal elevation at rest; broad shadows are reserved for floating or signature surfaces.
- **Border:** None by default. Soft dividers separate rows inside a shared container.
- **Internal Padding:** 18 dp by default, reduced to 16 dp or 4 dp vertical for dense row groups.

### Inputs / Fields

- **Style:** Material outlined text fields use the theme's medium shape and semantic color roles.
- **Focus:** Retain the native Material outline, label, cursor, and focus behavior.
- **Supporting Content:** Character counts sit in supporting text; optional notes expand in place, and the layout follows IME insets.

### Navigation

The compact-width app uses three Material destinations inside a floating 28 dp capsule. The selected destination receives a primary-container indicator with primary icon and label; inactive destinations use muted ink. Screen changes use restrained horizontal movement with a fade, while system Back remains authoritative in settings detail views and sheets.

### Liquid Balance Lobe

The dynamic asymmetric violet-to-magenta lobe (min-height 256 dp) is Penny's signature component. Its top eyebrow pairs the compact `TODAY'S FLOW` label with an `SMS LIVE` capsule, keeping capture status inside the financial summary rather than adding a separate header action. White type carries the focused label and amount; a cyan undertow and translucent white bloom create internal depth. The main amount stays on one line and steps between 24 sp and 46 sp to protect multi-digit values from clipping.

Two equal semantic radio segments (`In` and `Out`) sit inside a dark translucent capsule, eliminating redundancy with the hero "Spent today" display. Each segment uses unified 15 sp bold typography and whole-rupee formatting (`formatLobeAmount`), ensuring multi-digit values fit with full horizontal stability and symmetrical baselines. Selection moves a translucent highlight with a spring, while the lobe scales subtly to acknowledge non-default focus.

### Transaction Row

Each row is a full-width 46 dp icon field, a flexible title-and-account column, and a right-aligned amount/status column. Debit uses primary violet, credit uses theme-appropriate money green, and missing expense titles use primary color to signal pending work. Swiping right reveals a crimson 'Delete' action; tapping Delete requires device biometric authentication (Fingerprint, Face, or phone PIN/Pattern) before the movement is removed. Rows share one glass panel and use soft dividers between items.

### Theme Choice

System, Light, and Dark are equal radio choices presented as three minimum-76 dp tiles. The selected tile uses primary-container and primary content; unselected tiles use a translucent surface variant and muted content.

## Do's and Don'ts

### Do:

- **Do** keep the balance lobe visually dominant on the home screen.
- **Do** use semantic Material color and type roles so light, dark, and system themes remain accessible.
- **Do** group related rows inside one glass panel and preserve at least 48 dp touch targets.
- **Do** use spring motion for direct state selection and restrained fade/slide motion for navigation changes.
- **Do** preserve system Back, window insets, scalable text, radio semantics, and native control behavior.

### Don't:

- **Don't** flatten the home experience into a generic stack of equal finance cards.
- **Don't** scatter aurora gradients, colored shadows, or asymmetric lobes across routine components.
- **Don't** use green for non-financial success or use magenta/cyan as substitutes for error and credit semantics.
- **Don't** trade legibility for translucency, especially in dark mode or over ambient glow fields.
- **Don't** reuse the compact bottom navigation unchanged on an expanded tablet layout.
