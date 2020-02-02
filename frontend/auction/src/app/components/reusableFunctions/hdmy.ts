export function hdmy(t) {
    var years, months, days, hours;
    years = Math.floor(t / 31104000);
    t -= years * 31104000;
    months = Math.floor(t / 2592000) % 12;
    t -= months * 2592000;
    days = Math.floor(t / 86400) % 30;
    t -= days * 86400;
    hours = Math.floor(t / 3600) % 24;
    t -= hours * 3600;
    if (hours == 0) {
      hours = "";
    } else {
      hours = hours + "h";
    }
    
    if (days == 0) {
      days = "";
    } else {
      days = days + "d";
    }

    if (months == 0) {
      months = "";
    } else {
      months = months + "m";
    }

    if (years == 0) {
      years = "";
    } else {
      years = years + "y";
    }
    return [years, months, days, hours].join(" ");
  }