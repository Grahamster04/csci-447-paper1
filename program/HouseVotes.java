public class HouseVotes{
  // Overview: This data set includes votes for each of the U.S. House of Representatives Congressmen
  // on the 16 key votes identified by the Congressional Quarterly Almanac.
  // Predictor: Class: democrat, republican
  // Source: University of California, Irvine, 1987
  // URL: https://archive.ics.uci.edu/ml/datasets/Congressional+Voting+Records
  // Notes: Be careful with this data set since “?” does not indicate a missing attribute value. It actually
  // means “abstain.” This is a two-class problem.
  
  public String party;
  public char handicapped_infants;
  public char water_sharing;
  public char budget;
  public char fee_freeze;
  public char el_salvador;
  public char religion;
  public char satellite;
  public char nicaragua;
  public char missile;
  public char immigration;
  public char cutback;
  public char education;
  public char right_to_sue;
  public char crime;
  public char export;
  public char south_africa;
  
  HouseVotes(String p, char hi, char ws, char b, char ff, char es, char r, char s, char n, char m, char i, char cut, char e, char rts, char crim, char ex, char sa) {
    this.party = p;
    this.handicapped_infants = hi;
    this.water_sharing = ws;
    this.budget = b;
    this.fee_freeze = ff;
    this.el_salvador = es;
    this.religion; = r;
    this.satellite = s;
    this.nicaragua = n;
    this.misisle = m;
    this.immigration = i;
    this.cutback = cut;
    this.education = e;
    this.rgith_to_sue = rts;
    this.crime = crim;
    this.export = ex;
    this.south_africa = sa;
  }
}
