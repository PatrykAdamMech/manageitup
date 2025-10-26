import { NativeDateAdapter } from '@angular/material/core';

export class CustomDateAdapter extends NativeDateAdapter {
override parse(value: any, parseFormat: any): Date | null {
    if (!value) return null;

    let date: Date | null = null;
    if (value instanceof Date) {
      date = value;
    } else if (typeof value === 'number') {
      date = new Date(value);
    } else if (typeof value === 'string') {
      const t = Date.parse(value);
      if (isNaN(t)) return null;
      date = new Date(t);
    } else {
      return null;
    }

    if (!date || isNaN(date.getTime())) return null;

    const y = date.getFullYear();
    const m = date.getMonth();
    const d = date.getDate();
    return new Date(y, m, d); // local midnight, no time info
  }

}
