import { stringify } from "@angular/compiler/src/util";

export class FieldDetails
{
    displayFieldName: string | undefined;
    actualFieldName: string | undefined;
    
    constructor(displayFieldName: string, actualFieldName: string)
    {
        this.displayFieldName = displayFieldName;
        this.actualFieldName = actualFieldName;
    }
}