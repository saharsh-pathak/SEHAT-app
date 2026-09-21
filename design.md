# Design System Reference
**Inspired by a field-health worker mobile app (Maharashtra / ABHA style)**  
Use this as a style prompt or design token source — **not** a pixel-perfect recreation of any specific screen.

---

## 1. Overall Aesthetic

- **Mood**: Calm, trustworthy, government-health / public-service feel. Warm, approachable, low cognitive load for field workers.
- **Visual language**: Soft rounded cards on a warm off-white canvas. Gentle elevation, no harsh shadows. Generous but efficient spacing. Clear visual hierarchy with colour-coded action cards.
- **Platform feel**: Modern Android Material-ish with Indian public-health warmth (not pure Material 3 cold greys).
- **Density**: Comfortable for outdoor / one-handed use — large touch targets, readable text even in bright light.

---

## 2. Color Palette

### Core Surfaces
| Token              | Hex       | Usage |
|--------------------|-----------|-------|
| `bg-canvas`        | `#FAF7F2` | App background (warm cream / off-white) |
| `surface-card`     | `#FFFFFF` | Default elevated cards |
| `surface-soft`     | `#FBF2ED` | Subtle warm tint for secondary cards |

### Brand / Primary
| Token              | Hex       | Usage |
|--------------------|-----------|-------|
| `primary`          | `#852A2A` | Deep maroon-red — primary CTAs, header cards, important actions |
| `primary-dark`     | `#6B2222` | Pressed / darker variant |
| `primary-soft`     | `#FDEAEA` | Very light red tint for emergency / alert cards |

### Semantic Accents (Quick Action cards)
| Token              | Hex       | Usage |
|--------------------|-----------|-------|
| `accent-followup`  | `#F5EBE6` + purple icon | Follow-up tasks |
| `accent-medicine`  | `#E8F5E9` + green icon  | Medicine / stock |
| `accent-history`   | `#FAF4E8` + amber icon  | Patient history |
| `accent-emergency` | `#FDEAEA` + red icon    | Emergency referral |

### Status & Feedback
| Token              | Hex       | Usage |
|--------------------|-----------|-------|
| `success`          | `#2E7D32` | Online, synced, positive states |
| `success-bg`       | `#E8F5E9` | Soft green backgrounds |
| `warning`          | `#E65100` | (optional) |
| `error`            | `#C62828` | Critical alerts |

### Text
| Token              | Hex       | Usage |
|--------------------|-----------|-------|
| `text-primary`     | `#1A1A1A` | Headings, names, primary labels |
| `text-secondary`   | `#5C534C` | Subtitles, supporting text |
| `text-muted`       | `#8A8178` | Timestamps, helper text, placeholders |
| `text-on-primary`  | `#FFFFFF` | Text on maroon / dark red surfaces |
| `text-link`        | `#852A2A` | Links or secondary actions |

### Borders & Dividers
- Soft borders: `rgba(0,0,0,0.06)` or `#EDE7E0`
- Input borders: slightly stronger, warm grey

---

## 3. Typography

### Font Family
- **Primary**: System UI sans-serif (Roboto on Android, SF Pro on iOS, or Inter / Noto Sans as web fallback).
- Prefer a clean, highly legible humanist sans. Avoid decorative or condensed faces.
- Support for **Devanagari** (Marathi/Hindi) is required — use Noto Sans Devanagari or system equivalent for bilingual text.

### Scale & Weights (approximate mobile values)

| Role                    | Size (sp) | Weight    | Line height | Color token       | Notes |
|-------------------------|-----------|-----------|-------------|-------------------|-------|
| Greeting / small label  | 13–14     | Regular   | 1.3         | `text-secondary`  | “Good Morning,” |
| User name / title       | 18–20     | SemiBold  | 1.25        | `text-primary`    | “Sunita Tai” |
| Subtitle / role         | 13        | Regular   | 1.3         | `text-muted`      | “Medical Worker – Khed Block” |
| Section header          | 16–17     | SemiBold  | 1.3         | `text-primary`    | “Quick Actions” |
| Section helper          | 12–13     | Regular   | 1.3         | `text-muted`      | “For your daily work” |
| Card title              | 14–15     | Medium/SemiBold | 1.25   | `text-primary`    | “Follow-up Tasks” |
| Card description        | 12        | Regular   | 1.35        | `text-secondary`  | “Complete home visits” |
| Primary CTA heading     | 15–16     | SemiBold  | 1.25        | `text-on-primary` | “START PATIENT SCREENING” |
| Primary CTA sub         | 13–14     | Medium    | 1.3         | `text-on-primary` | “Search or Create Patient” |
| Input placeholder       | 14        | Regular   | —           | `text-muted`      | |
| Status / footer         | 12–13     | Regular   | 1.3         | `text-secondary` / `success` | |
| Bilingual tagline       | 13–14     | Medium    | 1.4         | `primary`         | Marathi + English |

**Letter-spacing**: Default / slightly tight on headings (−0.2 to 0). Normal on body.

---

## 4. Spacing & Layout

### Base unit
- **4 px** grid. Most spacing is multiples of 4 or 8.

### Common values
| Token          | Value   | Usage |
|----------------|---------|-------|
| `space-xs`     | 4 px    | Tight internal padding |
| `space-sm`     | 8 px    | Between related elements |
| `space-md`     | 12–16 px| Card internal padding, gap between cards |
| `space-lg`     | 20–24 px| Section gaps, screen horizontal padding |
| `space-xl`     | 32 px   | Major section separation |

### Screen structure (typical)
- Horizontal screen padding: **16–20 px**
- Top status / header area: comfortable safe-area + 12–16 px
- Vertical rhythm between major blocks: **16–24 px**
- Quick-action grid: **2-column**, gap **12 px**, equal height cards
- Bottom status bar / footer: fixed or sticky with **12–16 px** vertical padding

### Card internal padding
- Primary search card: **16–20 px** all sides
- Quick-action cards: **14–16 px**

---

## 5. Shape & Elevation

### Corner radius
| Element              | Radius     |
|----------------------|------------|
| Large primary cards  | 16–20 px   |
| Quick-action cards   | 14–16 px   |
| Chips / location tags| 20–24 px (pill) |
| Search input         | 12 px      |
| Avatar               | Full circle|
| Buttons (if any)     | 10–12 px   |

### Elevation / Shadow
- Very soft, warm-tinted shadows.
- Example: `0 2px 8px rgba(60, 40, 20, 0.06)` or `0 1px 4px rgba(0,0,0,0.06)`
- Avoid heavy Material drop shadows. Prefer subtle lift so cards feel “resting” on the cream background.

### Borders
- Most cards: no border or 1 px very light warm grey.
- Inputs: 1 px soft border + light fill.

---

## 6. Component Patterns

### Header / Profile strip
- Left: circular avatar (soft background or photo) + name stack
- Right: location chip (pill with pin icon) + notification bell (with optional red badge)

### Primary Search / CTA Card
- Full-width, deep maroon background (`primary`)
- White text hierarchy
- Contained search field with light background and search icon
- Strong visual weight — this is the main daily action

### Quick Action Grid
- 2 × 2 equal cards
- Each card has:
  - Soft pastel background matching its semantic colour
  - Icon in a slightly deeper tint of the same hue
  - Title (medium weight)
  - Short description (smaller, secondary)
  - Chevron or subtle affordance on the right
- Cards are tappable with clear press state (slight scale or opacity)

### Status footer
- Green check + “All records synced”
- Secondary timestamp
- Online indicator (green dot + “Online”)
- Soft green background or neutral with green accents

### Bilingual footer tagline
- Marathi line first (slightly larger or medium weight)
- English translation below or beside
- Small leaf / nature icon accent in brand green or primary

---

## 7. Iconography

- Simple, outlined or soft-filled icons
- Consistent stroke weight (~1.5–2 px)
- Colour-matched to the card’s semantic accent
- Size: 20–24 px for quick actions, 18–20 px for smaller UI

---

## 8. Interaction & Accessibility Notes

- Touch targets ≥ 48 × 48 dp
- High contrast text on cream and on maroon
- Support large system font sizes
- Offline / sync states must be immediately visible
- Prefer calm transitions (no flashy animations)

---

## 9. Prompt-ready summary (copy-paste)

```
Design a calm, trustworthy mobile health-worker app UI with:
- Warm cream background (#FAF7F2)
- Deep maroon primary (#852A2A) for main CTA cards
- Soft pastel semantic cards (light green, warm peach, light red) for quick actions
- Clean system sans-serif + Devanagari support
- Rounded cards (16–20px), very soft shadows
- Generous 16–20px horizontal padding, 12–16px card gaps
- Clear hierarchy: large name, medium section headers, small secondary text
- Bilingual (Marathi + English) footer with subtle leaf accent
- Overall aesthetic: approachable public-health tool for field use, not clinical or cold
```

---

*This document is a design-system extraction only. It intentionally avoids reproducing any specific layout, copy, or branding from the reference screenshot.*
