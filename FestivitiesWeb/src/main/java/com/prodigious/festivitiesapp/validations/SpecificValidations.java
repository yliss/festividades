package com.prodigious.festivitiesapp.validations;

import java.util.Date;

public class SpecificValidations 
{
	public boolean validateRangeDate(Date start,Date end)
	{
		if(end.before(start))
			return false;
		else
			return true;
	}
}
