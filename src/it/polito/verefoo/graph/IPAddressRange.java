package it.polito.verefoo.graph;

import it.polito.verefoo.utils.Range;

public class IPAddressRange implements Comparable<IPAddressRange>{
	
	Range firstByte;
	Range secondByte;
	Range thirdByte;
	Range fourthByte;
	
	IPAddress original;
	int wildcardPosition;
	
	public IPAddressRange() {
	}
	
	public IPAddressRange(IPAddress original) {
		
		this.original = original;
		
		firstByte = new Range(original.getFirstByte());
		secondByte = new Range(original.getSecondByte());
		thirdByte = new Range(original.getThirdByte());
		fourthByte = new Range(original.getFourthByte());
		
		wildcardPosition = original.hasWildcardsInByte();
	}
	
	public IPAddressRange(IPAddressRange copy) {
		this.firstByte = copy.getFirstByte();
		this.secondByte = copy.getSecondByte();
		this.thirdByte = copy.getThirdByte();
		this.fourthByte = copy.getFourthByte();
	}
	
	public IPAddressRange(Range firstByte, Range secondByte, Range thirdByte, Range fourthByte) {
		this.firstByte = firstByte;
		this.secondByte = secondByte;
		this.thirdByte = thirdByte;
		this.fourthByte = fourthByte;
	}
	

	public Range getFirstByte() {
		return firstByte;
	}

	public Range getSecondByte() {
		return secondByte;
	}

	public Range getThirdByte() {
		return thirdByte;
	}

	public Range getFourthByte() {
		return fourthByte;
	}

	public IPAddress getOriginal() {
		return original;
	}
	
	public int getWildcardPosition() {
		return wildcardPosition;
	}



	@Override
	public String toString() {
		return new String(firstByte + "." + secondByte + "."
				+ thirdByte + "." + fourthByte);
	}

	@Override
	public int compareTo(IPAddressRange o) {
		if(firstByte.compareTo(o.getFirstByte()) == 0) {
			if(secondByte.compareTo(o.getSecondByte()) == 0) {
				if(thirdByte.compareTo(o.getThirdByte()) == 0) {
					return fourthByte.compareTo(o.getFourthByte());
				}
				return thirdByte.compareTo(o.getThirdByte());
			}
			return secondByte.compareTo(o.getSecondByte());
		}
		
		return firstByte.compareTo(o.getFirstByte());
	}
	
	//true if this is included in other
	public boolean isIncludedIn(IPAddressRange other) {
		if(firstByte.isIncludedIn(other.getFirstByte()) && secondByte.isIncludedIn(other.getSecondByte())
				&& thirdByte.isIncludedIn(other.getThirdByte()) && fourthByte.isIncludedIn(other.getFourthByte()))
			return true;
		return false;
	}
	
	
	public Range getByteInPosition(int position) {
		if(position == 1)
			return firstByte;
		else if(position == 2)
			return secondByte;
		else if(position == 3)
			return thirdByte;
		else return fourthByte;
	}
	
	public void setByteInPosition(int position, Range r) {
		if(position == 1)
			this.firstByte = r;
		else if(position == 2)
			this.secondByte = r;
		else if(position == 3)
			this.thirdByte = r;
		else this.fourthByte = r;
	}
	
	//check if this is contiguous to o. In case it is, return the aggregation. Null otherwise
	public IPAddressRange isContiguousTo(IPAddressRange o) {
		//If all the bytes are equal a part from one that has min = o.max+1
		if(this.fourthByte.isContiguousTo(o.getFourthByte()) && this.thirdByte.equals(o.getThirdByte())
				&& this.secondByte.equals(o.getSecondByte()) && this.firstByte.equals(o.getFirstByte())) {
			
			IPAddressRange newipar = new IPAddressRange(o);
			Range newr = new Range(o.getFourthByte().getMin(), this.fourthByte.getMax());
			newipar.setByteInPosition(4, newr);
			return newipar;
			
		} else if(this.fourthByte.equals(o.getFourthByte()) && this.thirdByte.isContiguousTo(o.getThirdByte())
				&& this.secondByte.equals(o.getSecondByte()) && this.firstByte.equals(o.getFirstByte())) {
			
			IPAddressRange newipar = new IPAddressRange(o);
			Range newr = new Range(o.getThirdByte().getMin(), this.thirdByte.getMax());
			newipar.setByteInPosition(3, newr);
			return newipar;
			
		} else if(this.fourthByte.equals(o.getFourthByte()) && this.thirdByte.equals(o.getThirdByte())
				&& this.secondByte.isContiguousTo(o.getSecondByte()) && this.firstByte.equals(o.getFirstByte())) {
			
			IPAddressRange newipar = new IPAddressRange(o);
			Range newr = new Range(o.getSecondByte().getMin(), this.secondByte.getMax());
			newipar.setByteInPosition(2, newr);
			return newipar;
			
		} else if(this.fourthByte.equals(o.getFourthByte()) && this.thirdByte.equals(o.getThirdByte())
				&& this.secondByte.equals(o.getSecondByte()) && this.firstByte.isContiguousTo(o.getFirstByte())) {
			
			IPAddressRange newipar = new IPAddressRange(o);
			Range newr = new Range(o.getFirstByte().getMin(), this.firstByte.getMax());
			newipar.setByteInPosition(1, newr);
			return newipar;
		}
		
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		IPAddressRange o = (IPAddressRange) obj;
		
		if(this.firstByte.equals(o.getFirstByte()) && this.secondByte.equals(o.getSecondByte()) 
				&& this.thirdByte.equals(o.getThirdByte()) && this.fourthByte.equals(o.getFourthByte()))
			return true;
		return false;
	}
	
	
	
}
